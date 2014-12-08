package io.core9.editor.abtest;

import java.util.ArrayList;
import java.util.List;


public class VariationImpl implements Variation {

	@SuppressWarnings("unused")
	private String name;
	private List<MicroVariation> microVariationRegistry = new ArrayList<MicroVariation>();

	public VariationImpl(String name) {
		this.name = name;
	}

	@Override
	public void addMicroVariation(MicroVariation microVariation) {
		microVariationRegistry.add(microVariation);
	}

}
