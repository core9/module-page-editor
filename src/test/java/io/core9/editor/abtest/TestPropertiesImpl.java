package io.core9.editor.abtest;

import java.util.List;

public class TestPropertiesImpl implements TestProperties {

	private List<String> propertyOrder;



	@Override
	public List<String> getPropertyOrder() {
		return propertyOrder;
	}

	@Override
	public void setPropertyOrder(List<String> propertyOrder) {
		this.propertyOrder = propertyOrder;
	}


}
