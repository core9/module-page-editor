package io.core9.editor;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.List;

import org.junit.Test;

public class TestPageParser {

	@Test
	public void testGettingBlocksFromPage() {

		String blockClassName = "block";

		URL url = this.getClass().getResource("/editor/front-page.html");
		File testPage = new File(url.getFile());

		if(testPage.exists())
			System.out.println("Ok file exists");

		Parser parser = new PageParserImpl(testPage, blockClassName);

		List<Block> blocks = parser.getBlocks();

		assertTrue(blocks.size() > 1);

	}

}
