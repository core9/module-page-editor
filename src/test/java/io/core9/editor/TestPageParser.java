package io.core9.editor;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

import org.junit.Test;

public class TestPageParser {

	private String blockClassName;
	private Parser parser;
	private List<Block> blocks;

	public void setupBlocksFromPage() {
		blockClassName = ".block";
		URL url = this.getClass().getResource("/editor/front-page.html");
		File testPage = new File(url.getFile());
		assertTrue(testPage.exists());
		parser = new PageParserImpl(testPage, blockClassName);
		blocks = parser.getBlocks();
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
		Path file = FileUtils.getFile("/fooo", "new-front-page.html");
		String content = parser.getOriginalFile();
		file = FileUtils.writeToFile(file, content);
		String string = FileUtils.readPathToString(file);
		assertTrue(string.replaceAll("\\s+", "").trim().equals(content.replaceAll("\\s+", "").trim()));
	}

	@Test
	public void testReplaceElementWithNewElement(){
		//http://stackoverflow.com/questions/12419056/i-am-looking-for-an-html-parser-that-can-replace-the-small-tag-with-spans
		setupBlocksFromPage();
		//String originalContent = parser.getOriginalFile();
		//Block block = parser.getBlock(1);
		//parser.replaceBlock(1, block);
		
		//System.out.println(originalContent);
		
		//assertTrue(originalContent.equals(originalContent));
	}

	
	
	@Test
	public void testAssemblePageFromBlocks(){
		
		setupBlocksFromPage();
		
		//System.out.println(parser.getPage());
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
