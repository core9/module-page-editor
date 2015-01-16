package io.core9.editor;

import java.util.List;

public interface PageParser {

	List<Block> getBlocks();

	void switchBlocks(int i, int j);

	Block getBlock(int i);

	String getOriginalFile();

	void replaceBlock(int i, Block block);

	String getPage();

	void appendBlock(Block block);

	void insertBlock(int i, Block block);

	void deleteBlock(int i);

	void deleteAllBlocks();

	String getDataDirectory();




}
