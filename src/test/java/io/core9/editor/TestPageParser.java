package io.core9.editor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import io.core9.client.ClientRepositoryImpl;
import io.core9.editor.data.ClientData;
import io.core9.server.BlockImpl;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.minidev.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.junit.Test;

public class TestPageParser {

	private static final String pathPrefix = "data/test-editor";
	private AssetsManager assetsManager;
	private EditorRequest request;
	private ClientRepositoryImpl clientRepository;
	private PageDataParser dataParser;

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	private PageParser parser;
	private String fullHtmlTestPage = "/editor/client/site/pages/template.html";

	public void setupBlocksFromPage() {
		setupWorkingDirectory();
		URL url = this.getClass().getResource(fullHtmlTestPage );
		File testPage = new File(url.getFile());
		assertTrue(testPage.exists());
		parser = new PageParserImpl(testPage, blockContainer, blockClassName);
		List<Block> blocks = parser.getBlocks();
		assertTrue(blocks.size() > 1);
	}

	@Test
	public void testGetBlock() {
		setupBlocksFromPage();
		Block block = parser.getBlock(1);
		assertTrue(block.getElement().toString().length() > 10);
	}

	@Test
	public void testSwitchBlocks() {
		setupBlocksFromPage();
		Block block1 = parser.getBlock(1);
		Block block4 = parser.getBlock(4);
		parser.switchBlocks(1, 4);
		Block newBlock1 = parser.getBlock(1);
		Block newBlock4 = parser.getBlock(4);
		assertTrue(block1.getElement().toString().equals(newBlock4.getElement().toString()));
		assertTrue(block4.getElement().toString().equals(newBlock1.getElement().toString()));
		assertTrue(!block1.getElement().toString().equals(newBlock1.getElement().toString()));
		assertTrue(!block4.getElement().toString().equals(newBlock4.getElement().toString()));
	}

	@Test
	public void testWriteFile() {
		Path file = FileUtils.getFile("/foo", "new-front-page.html");
		file = FileUtils.writeToFile(file, "hello world");
		String string = FileUtils.readPathToString(file);
		assertTrue(string.equals("hello world"));
	}

	@Test
	public void testIfSavedFileIsRestoredCorrect() {
		setupBlocksFromPage();
		Path file = FileUtils.getFile("/fooo", "template.html");
		String originalContent = parser.getOriginalFile();
		file = FileUtils.writeToFile(file, originalContent);
		String content = FileUtils.readPathToString(file);
		assertTrue(isEqual(originalContent, content));
	}

	@Test
	public void testReplaceElementWithNewElement() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		Block block = parser.getBlock(2);
		parser.replaceBlock(0, block);
		String content = parser.getPage();
		assertTrue(!isEqual(originalContent, content));
		assertTrue(findBlockContaining("1 block", content) == 0);
		assertTrue(findBlockContaining("3 block", content) == 2);
	}

	@Test
	public void testAssemblePageFromBlocksAfterAppendingBlock() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		Block block = parser.getBlock(2);
		parser.appendBlock(block);
		String content = parser.getPage();
		assertTrue(!isEqual(originalContent, content));
		assertTrue(findBlockContaining("3 block", content) == 2);
	}

	@Test
	public void testAssemblePageFromBlocksAfterAppendingThreeBlocks() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		Block block = parser.getBlock(2);
		parser.appendBlock(block);
		parser.appendBlock(block);
		parser.appendBlock(block);
		String content = parser.getPage();
		assertTrue(!isEqual(originalContent, content));
		assertTrue(findBlockContaining("3 block", content) == 4);
	}

	@Test
	public void testAssemblePageFromBlocksAfterInsertBlock() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		Block block = parser.getBlock(2);
		parser.insertBlock(4, block);
		String content = parser.getPage();
		assertTrue(!originalContent.equals(content));
		assertTrue(findBlockContaining("3 block", content) == 2);
	}

	@Test
	public void testAssemblePageFromBlocksAfterDeleteBlock() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		parser.deleteBlock(0);
		String content = parser.getPage();
		assertTrue(!originalContent.equals(content));
		assertTrue(findBlockContaining("1 block", content) == 0);
	}

	@Test
	public void testAssemblePageFromBlocksAfterDeletingMultipleBlocks() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		parser.deleteBlock(0);
		parser.deleteBlock(2);
		String content = parser.getPage();
		assertTrue(!originalContent.equals(content));
		assertTrue(findBlockContaining("1 block", content) == 0);
		assertTrue(findBlockContaining("4 block", content) == 0); // not 3 but
																	// block 4
																	// since it
																	// is a list
	}

	@Test
	public void testAssemblePageFromBlocksAfterDeletingAllBlocks() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		parser.deleteAllBlocks();
		String content = parser.getPage();
		assertTrue(!originalContent.equals(content));
		assertAllBlocksAreDeleted(content);

	}

	@Test
	public void testAddBlockToEmptyContainer() {
		setupBlocksFromPage();
		String originalContent = parser.getOriginalFile();
		parser.deleteAllBlocks();
		String content = parser.getPage();
		assertTrue(!originalContent.equals(content));
		assertAllBlocksAreDeleted(content);
		String string = "<figure class=\"5 block\"><img src=\"placeholders/txt_(620x430).jpg\"></figure>";
		Element element = Jsoup.parse(string, "", Parser.xmlParser());
		Block block = new BlockImpl();
		block.addElement(element);
		parser.insertBlock(0, block);
		String newContent = parser.getPage();
		assertTrue(findBlockContaining("5 block", newContent) == 1);
	}

	private void assertAllBlocksAreDeleted(String content) {
		assertTrue(findBlockContaining("1 block", content) == 0);
		assertTrue(findBlockContaining("2 block", content) == 0);
		assertTrue(findBlockContaining("3 block", content) == 0);
		assertTrue(findBlockContaining("4 block", content) == 0);
		assertTrue(findBlockContaining("5 block", content) == 0);
		assertTrue(findBlockContaining("6 block", content) == 0);
		assertTrue(findBlockContaining("7 block", content) == 0);
	}

	private int findBlockContaining(String string, String content) {
		Pattern pattern = Pattern.compile(string.toLowerCase());
		Matcher matcher = pattern.matcher(content.toLowerCase());
		int count = 0;
		while (matcher.find())
			count++;
		return count;
	}

	private boolean isEqual(String originalContent, String content) {
		return originalContent.replaceAll("\\s+", "").trim().equals(content.replaceAll("\\s+", "").trim());
	}

	@SuppressWarnings("unused")
	private void printContent(String originalContent, String content) {
		System.out.println(originalContent);
		System.out.println(content);
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
		String page = assetsManager.getPageTemplate();
		dataParser = new PageDataParserImpl(page);
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
