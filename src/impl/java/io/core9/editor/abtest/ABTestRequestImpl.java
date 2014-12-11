package io.core9.editor.abtest;

public class ABTestRequestImpl implements ABTestRequest {

	private String domain;
	private String path;
	private long timestamp;
	private String abSessionId;
	private GeoLocation geoLocation;
	private String protocol;

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

	//


	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public long getTimeStamp() {
		 return timestamp;
	}

	@Override
	public  String getABSessionId() {
		return abSessionId;
	}

	@Override
	public  GeoLocation getGeoLocation() {
		return geoLocation;
	}

	@Override
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

}
