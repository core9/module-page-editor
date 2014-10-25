package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.AfterClass;
import org.junit.Test;

public class TestAssetManager {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private EditorRequest request;
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

	//FIXME test fails in jenkins git stuff..
	@Test
	public void testDownloadBlockFromGit() throws FileNotFoundException, InterruptedException {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.cloneBlocksFromGit("https://github.com/jessec/block-video.git");

		assertTrue(assetsManager.checkIfRepositoryDirectoryExists());
	}

	//FIXME test fails in file dir stuff..
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

	//FIXME test fails in jenkins git stuff..
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
	public void testGetStaticFilePath() {

		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);

		String filename = "/site/assets/css/core.css";
		String filePath = assetsManager.getStaticFilePath(filename);
		String shouldbe = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/assets/css/core.css";
		assertTrue(shouldbe.equals(filePath));

		String shouldBeBlockFilename = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/blocks/block-video/video/assets/css/style.css";
		String blockFilename = "/site/blocks/block-video/video/assets/css/style.css";
		String blockFilePath = assetsManager.getStaticFilePath(blockFilename);
		assertTrue(shouldBeBlockFilename.equals(blockFilePath));

	}

	@Test
	public void testWriteReadPageData() {
		setupWorkingDirectory();
		setUpRequest();
		assetsManager.setRequest(request);
		assetsManager.getPageTemplate();
		String testJsonFileName = "/editor/client/site/pages/test-json-data.json";
		URL url = this.getClass().getResource(testJsonFileName);
		File testJsonFile = new File(url.getFile());
		assertTrue(testJsonFile.exists());
		String jsonString = readFile(testJsonFile.getAbsolutePath(), StandardCharsets.UTF_8);

		request.setAbsoluteUrl("http://localhost:8080/site/data/?page=/easydrain&state=edit-block-0-type-icon");

		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/easydrain/data/block-0-type-icon.json";

		assetsManager.setRequest(request);

		JSONObject jsonData = (JSONObject) JSONValue.parse(jsonString);
		JSONObject meta = (JSONObject) jsonData.get("meta");
		JSONObject editorData = (JSONObject) jsonData.get("data");
		assetsManager.saveBlockData(meta, editorData);

		String dataStr = assetsManager.getPageDataRequest();

		assertTrue(expected.equals(dataStr));

	}

	private static String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, encoding);
	}

	@Test
	public void testGetPageDataFromUrl() throws MalformedURLException, UnsupportedEncodingException {
		setupWorkingDirectory();
		setUpRequest();

		request.setAbsoluteUrl("http://localhost:8080/site/data/?page=/easydrain&state=edit-block-0-type-icon");

		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/easydrain/data/block-0-type-icon.json";

		assetsManager.setRequest(request);
		String dataStr = assetsManager.getPageDataRequest();

		assertTrue(expected.equals(dataStr));

	}

}
