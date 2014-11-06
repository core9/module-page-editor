package io.core9.editor;

import io.core9.editor.server.BlockTool;
import io.core9.editor.server.RecreateClientSiteEnvironment;
import net.minidev.json.JSONObject;

public class TestFlusher {



	//@Test
	public void testFlusher(){

		BlockTool blockTool = new RecreateClientSiteEnvironment();
		JSONObject data = new JSONObject();

		data.put("host", "localhost");
		data.put("url", "http://localhost/test");
		blockTool.setData("data/editor",data);

	}

}
