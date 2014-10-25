package io.core9.editor;

import io.core9.server.BlockCommandImpl;
import io.core9.server.BlockTool;

import org.junit.Test;

import net.minidev.json.JSONObject;

public class TestFlusher {



	//@Test
	public void testFlusher(){

		BlockTool blockTool = new BlockCommandImpl();
		JSONObject data = new JSONObject();

		data.put("host", "localhost");
		data.put("url", "http://localhost/test");
		blockTool.setData("data/editor",data);

	}

}
