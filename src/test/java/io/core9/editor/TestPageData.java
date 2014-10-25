package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.Test;

public class TestPageData {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private EditorRequest request;
	@SuppressWarnings("unused")
	private String clientId = "9a8eccd84f9c40c791281139a87da7b645f25fab";
	private ClientRepositoryImpl clientRepository;

	@Test
	public void testCreateClientDirectory() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);

		assetsManager.getPageData();

	}

	private void setupWorkingDirectory() {
		assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	private void setUpRequest() {
		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		request = new RequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/easydrain");
	}

	@AfterClass
	public static void cleanup() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}

}
