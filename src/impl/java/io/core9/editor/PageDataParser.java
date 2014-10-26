package io.core9.editor;

import java.util.List;

public interface PageDataParser {


	void switchBlockData(int i, int j);

	BlockData getBlockData(int i);

	void replaceBlock(int i, BlockData blockData);

	void appendBlockData(BlockData blockData);

	void insertBlockData(int i, BlockData blockData);

	void deleteBlockData(int i);

	void deleteAllBlockData(String directory);

	List<BlockData> getAllBlockData();


}
