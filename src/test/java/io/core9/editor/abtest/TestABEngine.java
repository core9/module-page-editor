package io.core9.editor.abtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.client.ClientRepositoryImpl;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class TestABEngine {

	private static final String pathPrefix = "data/test-editor-variations";
	private AssetsManager assetsManager;
	private EditorRequest request;
	private String clientId = "9a8eccd84f9c40c791281139a87da7b645f25fab";
	private ClientRepositoryImpl clientRepository;
	private String httpsRepositoryUrl = "https://github.com/jessec/site-core9.git";

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
		request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/easydrain");
	}

	@After
	public void cleanup() {

		ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		EditorRequest request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/easydrain");
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}



	public void setUpSiteEnvironment(){
		setupWorkingDirectory();
		request.setAbsoluteUrl("http://localhost:8080/site/data/?page=/easydrain&state=edit-block-0-type-icon");
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.createSiteDirectory();
		assetsManager.clonePublicSiteFromGit(httpsRepositoryUrl);
	}


	@Test
	public void testCreateABTests(){
		setUpSiteEnvironment();

		String path = assetsManager.getPageTemplatePath();

		System.out.println(path);

	}

}
