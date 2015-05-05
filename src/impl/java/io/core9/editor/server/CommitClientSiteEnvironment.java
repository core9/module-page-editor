package io.core9.editor.server;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.data.ClientData;
import net.minidev.json.JSONObject;

public class CommitClientSiteEnvironment implements BlockTool {

	private String pathPrefix;
	private AssetsManager assetsManager;
	private EditorRequestImpl request;


	@Override
	public void setData(String prefix, JSONObject data) {
		this.pathPrefix = prefix;
		process(data);
	}

	private void process(JSONObject data) {
		request = new EditorRequestImpl();
		request.setClientRepository(ClientData.getRepository());
		request.setAbsoluteUrl(data.getAsString("url"));
		assetsManager = new AssetsManagerImpl(pathPrefix, request);



		assetsManager.commitPublicSiteToGit(assetsManager.getSiteDirectory(), data.getAsString("siterepo"), data.getAsString("user"), data.getAsString("password"));


	}

	@Override
	public String getResponse() {
		return null;
	}

}
