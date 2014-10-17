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

package io.core9.editor.data;

import io.core9.editor.model.Block;
import io.core9.editor.model.Category;
import io.core9.editor.model.Tag;

import java.util.ArrayList;
import java.util.List;



public class BlockData {
	static List<Block> blocks = new ArrayList<Block>();
	static List<Category> categories = new ArrayList<Category>();

	static {
		categories.add(createCategory(1, "Dogs"));
		categories.add(createCategory(2, "Cats"));
		categories.add(createCategory(3, "Rabbits"));
		categories.add(createCategory(4, "Lions"));

		blocks.add(createBlock(1, categories.get(1), "Cat 1", new String[] {
				"url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
		blocks.add(createBlock(2, categories.get(1), "Cat 2", new String[] {
				"url1", "url2" }, new String[] { "tag2", "tag3" }, "available"));
		blocks.add(createBlock(3, categories.get(1), "Cat 3", new String[] {
				"url1", "url2" }, new String[] { "tag3", "tag4" }, "pending"));

		blocks.add(createBlock(4, categories.get(0), "Dog 1", new String[] {
				"url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
		blocks.add(createBlock(5, categories.get(0), "Dog 2", new String[] {
				"url1", "url2" }, new String[] { "tag2", "tag3" }, "sold"));
		blocks.add(createBlock(6, categories.get(0), "Dog 3", new String[] {
				"url1", "url2" }, new String[] { "tag3", "tag4" }, "pending"));

		blocks.add(createBlock(7, categories.get(3), "Lion 1", new String[] {
				"url1", "url2" }, new String[] { "tag1", "tag2" }, "available"));
		blocks.add(createBlock(8, categories.get(3), "Lion 2", new String[] {
				"url1", "url2" }, new String[] { "tag2", "tag3" }, "available"));
		blocks.add(createBlock(9, categories.get(3), "Lion 3", new String[] {
				"url1", "url2" }, new String[] { "tag3", "tag4" }, "available"));

		blocks.add(createBlock(10, categories.get(2), "Rabbit 1", new String[] {
				"url1", "url2" }, new String[] { "tag3", "tag4" }, "available"));
	}

	public Block getBlockbyId(long blockId) {
		for (Block block : blocks) {
			if (block.getId() == blockId) {
				return block;
			}
		}
		return null;
	}

	public List<Block> findBlockByStatus(String status) {
		String[] statues = status.split(",");
		List<Block> result = new java.util.ArrayList<Block>();
		for (Block block : blocks) {
			for (String s : statues) {
				if (s.equals(block.getStatus())) {
					result.add(block);
				}
			}
		}
		return result;
	}

	public List<Block> findBlockByTags(String tags) {
		String[] tagList = tags.split(",");
		List<Block> result = new java.util.ArrayList<Block>();
		for (Block block : blocks) {
			if (null != block.getTags()) {
				for (Tag tag : block.getTags()) {
					for (String tagListString : tagList) {
						if (tagListString.equals(tag.getName()))
							result.add(block);
					}
				}
			}
		}
		return result;
	}

	public void addBlock(Block block) {
		if (blocks.size() > 0) {
			for (int i = blocks.size() - 1; i >= 0; i--) {
				if (blocks.get(i).getId() == block.getId()) {
					blocks.remove(i);
				}
			}
		}
		blocks.add(block);
	}

	static Block createBlock(long id, Category cat, String name, String[] urls,
			String[] tags, String status) {
		Block block = new Block();
		block.setId(id);
		block.setCategory(cat);
		block.setName(name);
		if (null != urls) {
			List<String> urlObjs = new ArrayList<String>();
			for (String urlString : urls) {
				urlObjs.add(urlString);
			}
			block.setPhotoUrls(urlObjs);
		}
		List<Tag> tagObjs = new java.util.ArrayList<Tag>();
		int i = 0;
		if (null != tags) {
			for (String tagString : tags) {
				i = i + 1;
				Tag tag = new Tag();
				tag.setId(i);
				tag.setName(tagString);
				tagObjs.add(tag);
			}
		}
		block.setTags(tagObjs);
		block.setStatus(status);
		return block;
	}

	static Category createCategory(long id, String name) {
		Category category = new Category();
		category.setId(id);
		category.setName(name);
		return category;
	}
}