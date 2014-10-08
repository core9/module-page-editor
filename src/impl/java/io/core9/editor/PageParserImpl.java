package io.core9.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParserImpl implements Parser {

	private Document doc;
	private List<Block> blockRegistry = new ArrayList<Block>();
	private File page;

	public PageParserImpl(File page, String blockClassName) {
		this.page = page;
		doc = parseHtml(page);
		parseBlocks(blockClassName);

	}

	private void parseBlocks(String blockClassName) {

		Elements elems = doc.select(blockClassName);

		for (Element elem : elems) {
			Block block = new BlockImpl();
			block.addElement(elem);
			blockRegistry.add(block);
		}
	}

	@Override
	public List<Block> getBlocks() {
		return blockRegistry;
	}

	private Document parseHtml(File page) {
		try {
			doc = Jsoup.parse(page, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public void switchBlocks(int i, int j) {
		blockRegistry.set(i, blockRegistry.set(j, blockRegistry.get(i)));
	}

	@Override
	public Block getBlock(int i) {
		return blockRegistry.get(i);
	}

	@Override
	public File getOriginalFile() {
		return page;
	}

}
