package io.core9.editor;

import org.jsoup.nodes.Element;

public class BlockImpl implements Block {

	private Element elem;

	@Override
	public void addElement(Element elem) {
		this.elem = elem;
	}

	@Override
	public Element getElement() {
		return elem;
	}

}
