package io.core9.editor;

import java.io.File;
import java.util.List;

public interface Parser {

	List<Block> getBlocks();

	void switchBlocks(int i, int j);

	Block getBlock(int i);

	File getOriginalFile();

}
