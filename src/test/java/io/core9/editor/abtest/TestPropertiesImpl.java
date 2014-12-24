package io.core9.editor.abtest;

import java.util.List;

public class TestPropertiesImpl implements TestProperties {

	private List<String> propertyOrder;

	@Override
	public void setOrder(List<String> propertyOrder) {
		this.propertyOrder = propertyOrder;
	}


}
