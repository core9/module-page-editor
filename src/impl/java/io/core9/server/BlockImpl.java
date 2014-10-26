package io.core9.server;

import io.core9.editor.Block;
import io.core9.editor.BlockData;

import org.jsoup.nodes.Element;

public class BlockImpl implements Block {

	private Element elem;
	private BlockData blockData;

	@Override
	public void addElement(Element elem) {
		this.elem = elem;
	}

	@Override
	public Element getElement() {
		return elem;
	}

	@Override
	public BlockData getBlockData() {
		return blockData;
	}

	@Override
	public void setBlockData(BlockData blockData) {
		this.blockData = blockData;
	}

}
