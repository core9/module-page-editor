package io.core9.editor;

import io.core9.server.BlockImpl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParserImpl implements PageParser {

	private Document container;
	private Document originalContainer;

	private List<Block> blockRegistry = new ArrayList<Block>();
	private File page;
	private String blockClassName;
	@SuppressWarnings("unused")
	private List<Block> originalBlockRegistry = new ArrayList<Block>();
	private File originalPage;
	private List<Integer> deletedBlocks = new ArrayList<Integer>();
	private String blockContainerId;
	private Document doc;
	private Document originalDoc;

	public PageParserImpl(File page, String blockContainerId, String blockClassName) {

		PageDataParser dataParser = new PageDataParserImpl(page);

		this.blockContainerId = blockContainerId;
		this.blockClassName = blockClassName;
		this.originalPage = page;
		this.page = page;
		doc = parseHtml(this.page);
		originalDoc = parseHtml(originalPage);
		container = getContainerFromHtml(this.page);
		originalContainer = getContainerFromHtml(originalPage);
		blockRegistry = parseBlocks(container, blockClassName);
		originalBlockRegistry = parseBlocks(originalContainer, blockClassName);
	}

	private Document getContainerFromHtml(File page) {
		return Jsoup.parse(parseHtml(page).select(blockContainerId).toString(), "UTF-8");
	}

	@Override
	public void deleteBlock(int x) {
		// check for out of bound
		try {
			blockRegistry.remove(x);
			deletedBlocks.add(x);
		} catch (Exception e) {

		}
	}

	@Override
	public void insertBlock(int i, Block block) {
		// check for out of bound
		blockRegistry.add(i, block);
	}

	@Override
	public void appendBlock(Block block) {
		blockRegistry.add(block);
	}

	@Override
	public String getOriginalFile() {
		return restoreHtmlPage(originalDoc, originalContainer, originalContainer);
	}

	@Override
	public String getPage() {
		return restoreHtmlPage(doc, originalContainer, container);
	}

	private String restoreHtmlPage(Document doc, Document originalContainer, Document container) {
		Element orgC = doc.select(blockContainerId).get(0);
		String pg = writeBlocksToString(container, blockRegistry);
		Document newDocument = Jsoup.parse(pg, "UTF-8");
		Element newC = newDocument.select(blockContainerId).get(0);
		changeBlock(doc, orgC, newC);
		return doc.toString();
	}

	@Override
	public Block getBlock(int i) {
		// check for out of bound
		return getBlocks().get(i);
	}

	@Override
	public List<Block> getBlocks() {
		return blockRegistry;
	}

	@Override
	public void switchBlocks(int i, int j) {
		// check for out of bound
		blockRegistry.set(i, blockRegistry.set(j, blockRegistry.get(i)));
	}

	@Override
	public void replaceBlock(int i, Block block) {
		// check for out of bound
		blockRegistry.set(i, block);
	}

	private String writeBlocksToString(Document document, List<Block> registry) {
		Elements elements = document.select(blockClassName);
		int i = 0;
		for (Element block : elements) {
			int n = i + 1;
			if (!deletedBlocks.contains(i) && elements.size() >= n && !registry.isEmpty()) {
				changeBlock(document, block, registry.get(i).getElement());
			} else {
				removeBlockFromList(i, block);
				i--;
			}
			i++;
		}
		if (registry.size() > elements.size()) {
			for (int x = elements.size(); x < registry.size(); x++) {
				if (registry.size() == 1) {
					document.select(blockContainerId).append(registry.get(x).getElement().toString());
				} else {
					document.select(blockClassName).last().after(registry.get(x).getElement().toString());
				}

			}
		}
		return document.toString();
	}

	private void changeBlock(Document document, Element block, Element newBlock) {
		block.wrap("<wrap></wrap>");
		Elements wrap = document.select("wrap");
		wrap.empty();
		wrap.html(newBlock.toString());
		wrap.unwrap();
	}

	private void removeBlockFromList(int i, Element block) {
		block.remove();
		for (Iterator<Integer> iter = deletedBlocks.listIterator(); iter.hasNext();) {
			Integer a = iter.next();
			if (a == i) {
				iter.remove();
			}
		}
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

	@Override
	public void deleteAllBlocks() {
		while (!blockRegistry.isEmpty()) {
			int size = blockRegistry.size();
			for (int i = 0; i < size; i++) {
				deleteBlock(i);
				deleteBlock(i);
			}
		}

	}

}
