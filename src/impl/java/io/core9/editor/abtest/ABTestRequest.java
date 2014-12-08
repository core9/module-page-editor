package io.core9.editor.abtest;

public interface ABTestRequest {

	void setDomain(String domain);

	void setPath(String path);

	void setTimeStamp(long l);

	void setABSessionId(String abSessionId);

	void setGeoLocation(GeoLocation geoLocation);

	String getPath();

	String getDomain();

	long getTimeStamp();

	String getABSessionId();

	GeoLocation getGeoLocation();

}
