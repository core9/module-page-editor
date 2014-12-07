package io.core9.editor.abtest;

import java.util.ArrayList;
import java.util.List;

public class ABStorageImpl implements ABStorage{

	private static ABStorage instance = null;
	private List<ABTest> testRegistry = new ArrayList<ABTest>();

	private ABStorageImpl() {
	}

	public static ABStorage getInstance() {
		if (instance == null) {
			instance = new ABStorageImpl();
		}
		return instance;
	}

	public static void clear(){
		instance = null;
	}

	@Override
	public void addTest(ABTest abTest) {
		testRegistry.add(abTest);
	}

	@Override
	public List<ABTest> getTests() {
		return testRegistry;
	}


}
