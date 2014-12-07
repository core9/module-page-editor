package io.core9.editor.abtest;

public class ABStorage {

	private static ABStorage instance = null;

	private ABStorage() {
	}

	public static ABStorage getInstance() {
		if (instance == null) {
			instance = new ABStorage();
		}
		return instance;
	}

	public Object getPathVersion(String abSessionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
