package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.core9.client.ClientRepositoryImpl;
import io.core9.editor.data.ClientData;
import net.minidev.json.JSONObject;

import org.junit.Test;

public class TestPageDataParser {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private EditorRequest request;
	private ClientRepositoryImpl clientRepository;
	private PageDataParserImpl dataParser;
	private String httpsRepositoryUrl;

	@Test
	public void getAllDataFromPage(){
		setupWorkingDirectory();

		assetsManager.deleteClientDirectory();
		assetsManager.createClientDirectory();

		String clientId = assetsManager.getClientId();
		ClientRepository repository = ClientData.getRepository();
		String siteRepoUrl = repository.getSiteRepository(clientId);
		assetsManager.clonePublicSiteFromGit(siteRepoUrl);
		JSONObject config = assetsManager.getSiteConfig();
		System.out.println(config);

		String page = assetsManager.getPageTemplate();

		dataParser = new PageDataParserImpl(page);
	}



	private void setupWorkingDirectory() {
		setUpRequest();
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	private void setUpRequest() {
		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		clientRepository.addSiteRepository("easydrain", "https://github.com/jessec/site-core9.git");
		request = new RequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/nl");
	}
}
