package io.core9.editor;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.AfterClass;
import org.junit.Test;

public class TestAssetManager {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private Request request;
	private String clientId = "9a8eccd84f9c40c791281139a87da7b645f25fab";
	private String urlId = "1b8b414deda107596d4fb4af7968a1122a654794";
	private ClientRepositoryImpl clientRepository;

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

	@Test
	public void testCreateIdFromUrl() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertTrue(urlId.equals(assetsManager.getUrlId()));
	}

	@Test
	public void testCreateIdFromClient() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertTrue(clientId.equals(assetsManager.getClientId()));
	}

	@Test
	public void testCreateClientDirectory() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertFalse(assetsManager.checkClientDirectory());
		assetsManager.createClientDirectory();
		assertTrue(assetsManager.checkClientDirectory());
	}

	@Test
	public void testDeleteClientDirectory() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.createClientDirectory();
		assertTrue(assetsManager.checkClientDirectory());
		assetsManager.deleteClientDirectory();
		assertFalse(assetsManager.checkClientDirectory());
	}

	@Test
	public void testDownloadBlockFromGit() throws FileNotFoundException, InterruptedException {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.cloneBlocksFromGit("https://github.com/jessec/block-video.git");

		assertTrue(assetsManager.checkIfRepositoryDirectoryExists());
	}

	@Test
	public void testCreateSiteDirectory() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assertFalse(assetsManager.checkSiteDirectory());
		assetsManager.deleteClientDirectory();
		assetsManager.createSiteDirectory();
		assertTrue(assetsManager.checkSiteDirectory());
	}



	@Test
	public void testDeleteSiteDirectory() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.createSiteDirectory();
		assertTrue(assetsManager.checkSiteDirectory());
		assetsManager.deleteSiteDirectory();
		assertFalse(assetsManager.checkSiteDirectory());
	}

	@Test
	public void testCloneSiteFromGit() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.clonePublicSiteFromGit("https://github.com/jessec/site-core9.git");
		assertTrue(assetsManager.checkSite());
		System.out.println(assetsManager.getSiteConfig());
	}


	@Test
	public void testCreateWorkingDirectory() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	@Test
	public void testDeleteWorkingDirectory() {
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}

	@Test
	public void testGetStaticFilePath(){

		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		String filename =  "/site/assets/css/core.css";
		String filePath = assetsManager.getStaticFilePath(filename);

		String shouldbe = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/assets/css/core.css";
		assertTrue(shouldbe.equals(filePath));

		String shouldBeBlockFilename =  "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/blocks/block-video/video/assets/css/style.css";
		String blockFilename = "/site/blocks/block-video/video/assets/css/style.css";
		String blockFilePath = assetsManager.getStaticFilePath(blockFilename );
		assertTrue(shouldBeBlockFilename.equals(blockFilePath));


	}

}
