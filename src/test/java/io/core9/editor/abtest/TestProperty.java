package io.core9.editor.abtest;

public enum TestProperty {

	VERSION("version"), USER_AGENT("ua"), REQUEST("req"), GEO_LOCATION("geo"), PERIOD("period"), PERCENTAGE("percentage");
	private String code;

	private TestProperty(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
}
