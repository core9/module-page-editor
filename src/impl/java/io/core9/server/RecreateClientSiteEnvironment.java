package io.core9.server;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.RequestImpl;
import io.core9.editor.data.ClientData;

import java.io.FileNotFoundException;
import java.util.List;

import net.minidev.json.JSONObject;

public class RecreateClientSiteEnvironment implements BlockTool {

	private String pathPrefix;
	private AssetsManager assetsManager;
	private RequestImpl request;


	@Override
	public void setData(String prefix, JSONObject data) {
		this.pathPrefix = prefix;
		process(data);
	}

	private void process(JSONObject data) {
		request = new RequestImpl();
		request.setClientRepository(ClientData.getRepository());
		request.setAbsoluteUrl(data.getAsString("url"));
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		if (!assetsManager.checkWorkingDirectory()) {
			assetsManager.createWorkingDirectory();
		}
		assetsManager.deleteClientDirectory();
		assetsManager.createClientDirectory();
		assetsManager.clonePublicSiteFromGit(ClientData.getRepository().getSiteRepository(assetsManager.getClientId()));
		try {
			List<String> blockRepos = ClientData.getRepository().getBlockRepository(assetsManager.getClientId());
			for(String repo : blockRepos){
				assetsManager.cloneBlocksFromGit(repo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getResponse() {
		return null;
	}

}
