package io.core9.editor;

import java.util.List;

public interface PageDataParser {

	List<BlockData> getAllBlockData();

	void switchBlockData(int i, int j);

	BlockData getBlockData(int i);

	String getOriginalFile();

	void replaceBlock(int i, BlockData blockData);

	String getPage();

	void appendBlock(BlockData blockData);

	void insertBlock(int i, BlockData blockData);

	void deleteBlock(int i);

	void deleteAllBlocks();


}
