package io.core9.editor.abtest;

public enum TestProperty {

	VERSION("version"), USER_AGENT("userAgent"), REQUEST("request"), GEO_LOCATION("geo"), PERIOD("period"), PERCENTAGE("percentage");
	private String code;

	private TestProperty(String c) {
		code = c;
	}

	public String getCode() {
		return code;
	}
}

/**
 *
{
  "userAgent" : null,
  "percentage" : null,
  "version" : null,
  "active" : false,
  "name" : "name",
  "domain" : "easydrain.localhost",
  "path" : "/p/scraper/nl",
  "startDate" : 1419424995856,
  "endDate" : 1419424995856,
  "timeRange" : [ ],
  "includedLocations" : [ ],
  "excludedLocations" : [ ],
  "request" : "",
  "tests" : [ ]
}

 *
 */
