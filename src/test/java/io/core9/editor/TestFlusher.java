package io.core9.editor;

import org.junit.Test;

import io.core9.editor.resource.BlockCommandImpl;
import io.core9.editor.resource.BlockTool;
import net.minidev.json.JSONObject;

public class TestFlusher {



	@Test
	public void testFlusher(){

		BlockTool blockTool = new BlockCommandImpl();
		JSONObject data = new JSONObject();

		data.put("host", "localhost");
		data.put("url", "http://localhost/test");
		blockTool.setData("data/editor",data);

	}

}
