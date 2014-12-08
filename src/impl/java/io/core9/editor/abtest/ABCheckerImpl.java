package io.core9.editor.abtest;

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
