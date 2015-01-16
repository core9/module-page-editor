package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;
import io.core9.editor.abtest.entities.ABTestRequest;

import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("unused")
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

	@Override
	public String getIdentifier(ABTestRequest request) {
		return getIdentifiers(request);
	}

	private String getIdentifiers(ABTestRequest request) {

		List<ABTest> tests = getTestsFromRequest(request);

		return null;
	}

	private List<ABTest> getTestsFromRequest(ABTestRequest request) {

		List<ABTest> result = new ArrayList<ABTest>();

		for(ABTest test : testRegistry){
			if(isActiveTest(test, request)){
				result.add(test);
			}
		}

		return result;
	}

	private boolean isActiveTest(ABTest test, ABTestRequest request) {
		ABChecker abChecker = new ABCheckerImpl(test, request);
		return abChecker.result();
	}


}
