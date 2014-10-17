package io.core9.editor;

import org.jsoup.nodes.Element;

public interface Block {

	void addElement(Element elem);

	Element getElement();

}
