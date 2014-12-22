package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;
import io.core9.editor.abtest.entities.ABTestRequest;

public class ABCheckerImpl implements ABChecker {

	private ABTest test;
	private ABTestRequest request;

	public ABCheckerImpl(ABTest test, ABTestRequest request) {
		this.test = test;
		this.request = request;
	}

	@Override
	public boolean result() {
		checkIfTestMachesRequest();
		return false;
	}

	private void checkIfTestMachesRequest() {
		if(!request.getPath().equals(request.getPath())){
			
		}
	}

}
