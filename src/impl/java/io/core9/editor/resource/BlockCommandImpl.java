package io.core9.editor.resource;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.ClientRepository;
import io.core9.editor.ClientRepositoryImpl;
import io.core9.editor.RequestImpl;

import java.io.FileNotFoundException;

import net.minidev.json.JSONObject;

public class BlockCommandImpl implements BlockTool {

	private static final String pathPrefix = "data/editor";
	private AssetsManager assetsManager;
	private RequestImpl request;
	private String httpsSiteRepositoryUrl = "https://github.com/jessec/site-kennispark.git";
	private String httpsBlockRepositoryUrl = "https://github.com/jessec/block-video.git";
	private ClientRepository clientRepository;

	@Override
	public void setData(JSONObject data) {
		process(data);
	}

	private void process(JSONObject data) {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		if (!assetsManager.checkWorkingDirectory()) {
			assetsManager.createWorkingDirectory();
		}

		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		request = new RequestImpl();
		request.setClientRepository(clientRepository);
		String absoluteUrl = "http://localhost:8080/nl/";
		request.setAbsoluteUrl(absoluteUrl);

		assetsManager.setRequest(request);

		assetsManager.createClientDirectory();

		assetsManager.clonePublicSiteFromGit(httpsSiteRepositoryUrl);
		JSONObject config = assetsManager.getSiteConfig();
		System.out.println(config);

		try {
			assetsManager.cloneBlocksFromGit(httpsBlockRepositoryUrl);
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
