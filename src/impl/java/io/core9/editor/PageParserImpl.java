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
	private Document orgDoc;
	private List<Block> blockRegistry = new ArrayList<Block>();
	private File page;
	private String blockClassName;
	private List<Block> originalBlockRegistry = new ArrayList<Block>();
	private File originalPage;

	public PageParserImpl(File page, String blockClassName) {

		this.blockClassName = blockClassName;

		this.originalPage = page;
		this.page = page;

		doc = parseHtml(this.page);
		orgDoc = parseHtml(originalPage);

		blockRegistry = parseBlocks(doc, blockClassName);
		originalBlockRegistry = parseBlocks(orgDoc, blockClassName);

	}


	@Override
	public void insertBlock(int i, Block block) {
		blockRegistry.add(i, block);
	}
	
	@Override
	public void appendBlock(Block block) {
		blockRegistry.add(block);
	}

	@Override
	public String getOriginalFile() {
		return writeBlocksToString(orgDoc, originalPage, originalBlockRegistry);
	}

	@Override
	public String getPage() {
		return writeBlocksToString(doc, page, blockRegistry);
	}

	@Override
	public Block getBlock(int i) {
		return blockRegistry.get(i);
	}

	@Override
	public List<Block> getBlocks() {
		return blockRegistry;
	}

	@Override
	public void switchBlocks(int i, int j) {
		blockRegistry.set(i, blockRegistry.set(j, blockRegistry.get(i)));
	}

	@Override
	public void replaceBlock(int i, Block block) {
		blockRegistry.set(i, block);
	}

	private String writeBlocksToString(Document document, File file, List<Block> registry) {
		int i = 0;
		for (Element block : document.select(blockClassName)) {
			block.wrap("<wrap></wrap>");
			Elements wrap = document.select("wrap");
			wrap.empty();
			Element elem = registry.get(i).getElement();
			wrap.html(elem.toString());
			wrap.unwrap();
			i++;
		}
		return document.toString();
	}

	private List<Block> parseBlocks(Document document, String blockClassName) {

		Elements elems = document.select(blockClassName);

		List<Block> registry = new ArrayList<Block>();
		for (Element elem : elems) {
			Block block = new BlockImpl();
			block.addElement(elem);
			registry.add(block);
		}
		return registry;
	}

	private Document parseHtml(File page) {
		Document document = null;
		try {
			document = Jsoup.parse(page, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return document;
	}


}
