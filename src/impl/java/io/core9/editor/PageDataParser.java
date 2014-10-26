package io.core9.editor;

import java.util.List;

public interface PageDataParser {


	void switchBlockData(int i, int j);

	BlockData getBlockData(int i);

	String getOriginalFile();

	void replaceBlock(int i, BlockData blockData);

	String getPage();

	void appendBlock(BlockData blockData);

	void insertBlock(int i, BlockData blockData);

	void deleteBlock(int i);

	void deleteAllBlockData(String directory);

	List<BlockData> getAllBlockData();


}
