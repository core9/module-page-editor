package io.core9.editor.abtest.entities;

public class GeoLocationImpl implements GeoLocation {

	@SuppressWarnings("unused")
	private String name;
	private String ipAddress;

	public GeoLocationImpl(String name) {
		this.name = name;
	}

	@Override
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

}
