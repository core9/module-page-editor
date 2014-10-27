package io.core9.server;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.EditorRequestImpl;
import net.minidev.json.JSONObject;

public class LoadClientSiteTemplatesAndKeepData implements BlockTool {

	@Override
	public String getResponse() {
		return null;
	}

	@Override
	public void setData(String prefix, JSONObject data) {

		EditorRequest request = new EditorRequestImpl();
		AssetsManager assetsManager = new AssetsManagerImpl("data/temp-editor", request);

	}

}
