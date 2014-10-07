package io.core9.editor;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

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
		// For a simple file system with Unix-style paths and behavior:
		FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
		Path foo = fs.getPath("/foo");
		try {
			Files.createDirectory(foo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Path hello = foo.resolve("new-front-page.html"); // /foo/new-front-page.html
		try {
			Files.write(hello, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(hello, Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuilder content = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				content.append(line).append("/n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Content: " + content.toString());

	}

}
