package io.core9.editor;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;
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
	public void testGetBlock(){
		setupBlocksFromPage();
		Block block = parser.getBlock(1);
		assertTrue(block.getElement().toString().length() > 10);
	}

	@Test
	public void testSwitchBlocks(){
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


}
