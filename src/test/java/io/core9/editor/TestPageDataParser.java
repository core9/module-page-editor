package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.core9.client.ClientRepositoryImpl;
import io.core9.editor.data.ClientData;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Test;

public class TestPageDataParser {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private EditorRequest request;
	private ClientRepositoryImpl clientRepository;
	private PageDataParser dataParser;




	@Test
	public void testGetAllDataFromPage() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
	}

	@Test
	public void testGetFirstDataBlockFromPage() {
		setupWorkingDirectory();
		BlockData blockData = dataParser.getBlockData(3);
		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/nl/data/block-3-type-smalltext.json";
		String result = blockData.getFilePath();
		assertTrue(expected.equals(result));
	}

	@Test
	public void testSwitchBlockData() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
		BlockData blockDataOrg = dataParser.getBlockData(2);
		String filePathOrg = blockDataOrg.getFilePath();
		dataParser.switchBlockData(2, 4);
		BlockData blockDataNew = dataParser.getBlockData(2);
		String filePathNew = blockDataNew.getFilePath();
		assertTrue(!filePathOrg.equals(filePathNew));
	}

	@Test
	public void testReplaceBlockData() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
		BlockData blockDataOrg = dataParser.getBlockData(5);
		String filePathOrg = blockDataOrg.getFilePath();
		BlockData blockDataNew = dataParser.getBlockData(0);
		dataParser.replaceBlock(5, blockDataNew);
		BlockData blockDataCheck = dataParser.getBlockData(5);
		String filePathCheck = blockDataCheck.getFilePath();
		assertTrue(!filePathOrg.equals(filePathCheck));
	}

	@Test
	public void testInsertBlockData() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
		BlockData blockData = dataParser.getBlockData(0);
		dataParser.insertBlockData(3, blockData);
		BlockData setBlockData = dataParser.getBlockData(3);
		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/nl/data/block-3-type-slider.json";
		assertTrue(expected.equals(setBlockData.getFilePath()));
		List<BlockData> newData = dataParser.getAllBlockData();
		assertTrue(newData.size() == 10);
	}

	@Test
	public void testAppendBlockData() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
		BlockData blockData = dataParser.getBlockData(0);
		dataParser.appendBlockData(blockData);
		BlockData setBlockData = dataParser.getBlockData(9);
		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/nl/data/block-9-type-slider.json";
		assertTrue(expected.equals(setBlockData.getFilePath()));
		List<BlockData> newData = dataParser.getAllBlockData();
		assertTrue(newData.size() == 10);
	}

	@Test
	public void testDeleteBlockData() {
		setupWorkingDirectory();
		List<BlockData> data = dataParser.getAllBlockData();
		assertTrue(data.size() == 9);
		dataParser.deleteBlockData(4);
		List<BlockData> newData = dataParser.getAllBlockData();
		assertTrue(newData.size() == 8);
	}

	@Test
	public void testWriteReadPageData() {
		setupWorkingDirectory();
		assetsManager.getPageTemplatePath();
		String testJsonFileName = "/editor/client/site/pages/test-json-data.json";
		URL url = this.getClass().getResource(testJsonFileName);
		File testJsonFile = new File(url.getFile());
		assertTrue(testJsonFile.exists());
		String jsonString = readFile(testJsonFile.getAbsolutePath(), StandardCharsets.UTF_8);
		request.setAbsoluteUrl("http://localhost:8080/site/data/?page=/easydrain&state=edit-block-0-type-icon");
		String expected = "data/test-editor/9a8eccd84f9c40c791281139a87da7b645f25fab/site/pages/localhost/easydrain/data/block-0-type-icon.json";
		JSONObject jsonData = (JSONObject) JSONValue.parse(jsonString);
		JSONObject meta = (JSONObject) jsonData.get("meta");
		JSONObject editorData = (JSONObject) jsonData.get("data");
		assetsManager.saveBlockData(meta, editorData);
		String dataStr = assetsManager.getPageDataRequest();
		assertTrue(expected.equals(dataStr));
	}

	private void setupWorkingDirectory() {
		setUpRequest();
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
		assetsManager.deleteClientDirectory();
		assetsManager.createClientDirectory();
		String clientId = assetsManager.getClientId();
		ClientRepository repository = ClientData.getRepository();
		String siteRepoUrl = repository.getSiteRepository(clientId);
		assetsManager.clonePublicSiteFromGit(siteRepoUrl);
		JSONObject config = assetsManager.getSiteConfig();
		System.out.println(config);
		String page = assetsManager.getPageTemplatePath();
		dataParser = new PageDataParserImpl(page);
	}

	private void setUpRequest() {
		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		clientRepository.addSiteRepository("easydrain", "https://github.com/jessec/site-core9.git");
		request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/nl");
	}


	@After
	public  void cleanupAfterMethod() {

		ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		EditorRequest request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/nl");
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}
	
	@AfterClass
	public static void cleanup() {

		ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("localhost", "easydrain");
		EditorRequest request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://localhost:8080/nl");
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
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
}
