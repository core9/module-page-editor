package io.core9.editor;

import io.core9.editor.AssetsManager;
import io.core9.editor.ClientRepository;
import io.core9.editor.data.ClientData;

import java.io.FileNotFoundException;
import java.util.List;

import net.minidev.json.JSONObject;

public class BlockCommandImpl implements BlockTool {

	private String pathPrefix;
	private AssetsManager assetsManager;
	private RequestImpl request;


	@Override
	public void setData(String prefix, JSONObject data) {
		this.pathPrefix = prefix;
		process(data);
	}

	private void process(JSONObject data) {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		//assetsManager.deleteWorkingDirectory();
		if (!assetsManager.checkWorkingDirectory()) {
			assetsManager.createWorkingDirectory();
		}



		request = new RequestImpl();
		request.setClientRepository(ClientData.getRepository());
		String absoluteUrl = data.getAsString("url");
		request.setAbsoluteUrl(absoluteUrl);

		assetsManager.setRequest(request);

		assetsManager.deleteClientDirectory();

		assetsManager.createClientDirectory();

		String clientId = assetsManager.getClientId();

		ClientRepository repository = ClientData.getRepository();

		String siteRepoUrl = repository.getSiteRepository(clientId);

		assetsManager.clonePublicSiteFromGit(siteRepoUrl);
		JSONObject config = assetsManager.getSiteConfig();
		System.out.println(config);

		try {

			List<String> blockRepos = repository.getBlockRepository(clientId);
			for(String repo : blockRepos){
				assetsManager.cloneBlocksFromGit(repo);
			}


		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	@Override
	public String getResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
