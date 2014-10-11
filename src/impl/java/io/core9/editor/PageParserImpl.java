package io.core9.editor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class PageParserImpl implements Parser {

	private Document container;
	private Document originalContainer;

	private List<Block> blockRegistry = new ArrayList<Block>();
	private File page;
	private String blockClassName;
	private List<Block> originalBlockRegistry = new ArrayList<Block>();
	private File originalPage;
	private List<Integer> deletedBlocks = new ArrayList<Integer>();
	private String blockContainerId;


	public PageParserImpl(File page, String blockContainerId, String blockClassName) {

		this.blockContainerId = blockContainerId;
		this.blockClassName = blockClassName;

		this.originalPage = page;
		this.page = page;

		container = getContainerFromHtml(this.page);
		originalContainer = getContainerFromHtml(originalPage);

		blockRegistry = parseBlocks(container, blockClassName);
		originalBlockRegistry = parseBlocks(originalContainer, blockClassName);

	}

	private Document getContainerFromHtml(File page) {
		Document document = parseHtml(page);
		return Jsoup.parse(document.select(blockContainerId).toString(), "UTF-8");
	}

	@Override
	public void deleteBlock(int x) {

		// check for out of bound
		blockRegistry.remove(x);
		deletedBlocks.add(x);

		System.out.println("");
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
		return writeBlocksToString(originalContainer, originalBlockRegistry);
	}

	@Override
	public String getPage() {
		return writeBlocksToString(container, blockRegistry);
	}

	@Override
	public Block getBlock(int i) {
		// check for out of bound
		return blockRegistry.get(i);
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
			if (!deletedBlocks.contains(i) && elements.size() >= n) {
				changeBlock(document, registry, i, block);
			} else {
				removeBlockFromList(i, block);
				i--;
			}
			i++;
		}
		if (registry.size() > elements.size()) {
			for (int x = elements.size(); x < registry.size(); x++) { // correct
				Block elem = registry.get(x);
				Element newElem = elem.getElement();
				Element element = document.select(blockClassName).last();
				element.after(newElem.toString());
				System.out.println("");
			}
		}
		return document.toString();
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

	private void changeBlock(Document document, List<Block> registry, int i, Element block) {
		block.wrap("<wrap></wrap>");
		Elements wrap = document.select("wrap");
		wrap.empty();
		Element elem = registry.get(i).getElement();
		wrap.html(elem.toString());
		wrap.unwrap();
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
