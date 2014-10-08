package io.core9.editor;

import java.util.List;

public interface Parser {

	List<Block> getBlocks();

	void switchBlocks(int i, int j);

	Block getBlock(int i);

	String getOriginalFile();

	void replaceBlock(int i, Block block);

	String getPage();

}
