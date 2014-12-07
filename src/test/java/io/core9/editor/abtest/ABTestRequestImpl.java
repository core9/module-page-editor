package io.core9.editor.abtest;

@SuppressWarnings("unused")
public class ABTestRequestImpl implements ABTestRequest {

	private String domain;
	private String path;
	private long timestamp;
	private String abSessionId;
	private GeoLocation geoLocation;

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void setTimeStamp(long l) {
		this.timestamp = l;
	}

	@Override
	public void setABSessionId(String abSessionId) {
		this.abSessionId = abSessionId;
	}

	@Override
	public void setGeoLocation(GeoLocation geoLocation) {
		this.geoLocation = geoLocation;
	}

}
