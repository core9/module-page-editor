/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.core9.editor.resource;

import io.core9.editor.data.BlockData;
import io.core9.editor.model.Block;
import io.core9.editor.model.Owner;
import io.core9.server.RecreateSiteEnvironment;
import io.core9.server.BlockTool;
import io.core9.server.BlockUpdateToolImpl;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;



@Path("/block")
@Api(value = "/block", description = "Operations about blocks")
@Produces({"application/json"})
public class BlockResource {
	static BlockData blockData = new BlockData();
	static JavaRestResourceUtil ru = new JavaRestResourceUtil();

/*	@GET
	@Path("/{blockId : [0-9]}")
	@ApiOperation(
		value = "Find block by ID",
		notes = "Returns a block when ID < 10. ID > 10 or nonintegers will simulate API error conditions",
		response = Block.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
			@ApiResponse(code = 404, message = "Block not found") })
	public Response getBlockById(
			@ApiParam(value = "ID of block that needs to be fetched", allowableValues = "range[1,5]", required = true) @PathParam("blockId") String blockId)
			throws NotFoundException {
		Block block = blockData.getBlockbyId(ru.getLong(0, 100000, 0, blockId));
		if (null != block) {
			return Response.ok().entity(block).build();
		} else {
			throw new NotFoundException(404, "Block not found");
		}
	}*/

	@POST
	@ApiOperation(value = "Add a new block to the store")
	@ApiResponses(value = { @ApiResponse(code = 405, message = "Invalid input") })
	public Response addBlock(
			@ApiParam(value = "Block object that needs to be added to the store", required = true) Block block) {
		blockData.addBlock(block);

		JSONObject data = (JSONObject) JSONValue.parse(block.getData());
		System.out.println(data);

		BlockTool blockTool = new BlockUpdateToolImpl();
		blockTool.setData("data/editor", data);
		//String status = blockTool.getResponse();

		return Response.ok().entity("SUCCESS").build();
	}

	@PUT
	@ApiOperation(value = "Update an existing block")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
			@ApiResponse(code = 404, message = "Block not found"),
			@ApiResponse(code = 405, message = "Validation exception") })
	public Response updateBlock(
			@ApiParam(value = "Block object that needs to be added to the store", required = true) Block block) {

		blockData.addBlock(block);

		return Response.ok().entity("SUCCESS").build();
	}

	@GET
	@Path("/findByStatus")
	@ApiOperation(
		value = "Finds Blocks by status",
		notes = "Multiple status values can be provided with comma seperated strings",
		response = Block.class,
		responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid status value") })
	public Response findBlocksByStatus(
			@ApiParam(value = "Status values that need to be considered for filter", required = true, defaultValue = "available", allowableValues = "available,pending,sold", allowMultiple = true) @QueryParam("status") String status) {

		BlockTool blockTool = new RecreateSiteEnvironment();
		JSONObject data = new JSONObject();
		data.put("action", status);
		blockTool.setData("data/editor",data);

		return Response.ok(blockData.findBlockByStatus(status)).build();
	}

	@GET
	@Path("/findByTags")
	@ApiOperation(
		value = "Finds Blocks by tags",
		notes = "Muliple tags can be provided with comma seperated strings. Use tag1, tag2, tag3 for testing.",
		response = Block.class,
		responseContainer = "List")
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid tag value") })
	@Deprecated
	public Response findBlocksByTags(
			@ApiParam(value = "Tags to filter by", required = true, allowMultiple = true) @QueryParam("tags") String tags) {
		return Response.ok(blockData.findBlockByTags(tags)).build();
	}

	@GET
	  @Path("/{blockId}/owner")
	  @ApiOperation(
	    value = "Gets the owner of a block",
	    response = OwnerResource.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Invalid ID supplied"),
			@ApiResponse(code = 404, message = "Block not found") })
	  public Response getOwner(@PathParam("blockId") String blockId) {

		Owner o = new Owner();
	    o.setName("Tony");
	    o.setId(1);
	    return Response.ok(o).build();
	  }
}
