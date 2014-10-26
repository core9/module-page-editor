package io.core9.editor;

import java.io.File;
import java.util.List;

public class PageDataParserImpl implements PageDataParser {

	private File pageFile;

	public PageDataParserImpl(File page) {
		this.pageFile = page;
	}

	@Override
	public List<Block> getBlocks() {

		return null;
	}

	@Override
	public void switchBlocks(int i, int j) {


	}

	@Override
	public Block getBlock(int i) {

		return null;
	}

	@Override
	public String getOriginalFile() {

		return null;
	}

	@Override
	public void replaceBlock(int i, BlockData blockData) {


	}

	@Override
	public String getPage() {

		return null;
	}

	@Override
	public void appendBlock(BlockData blockData) {


	}

	@Override
	public void insertBlock(int i, BlockData blockData) {


	}

	@Override
	public void deleteBlock(int i) {


	}

	@Override
	public void deleteAllBlocks() {


	}

}
