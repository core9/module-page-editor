package io.core9.editor.abtest;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class ABTestImpl implements ABTest {


	private String name;
	private String domain;
	private String path;
	private Date startDate;
	private Date endDate;
	private List<ABDay> timeRange;
	private List<GeoLocation> includedLocations;
	private List<GeoLocation> excludedLocations;

	public ABTestImpl(String name) {
		this.name = name;
	}

	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void setDateRange(Date startDate, Date endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public void setTimeRange(List<ABDay> timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public void setIncludedGeoLocations(List<GeoLocation> locations) {
		this.includedLocations = locations;
	}

	@Override
	public void setExcludedGeoLocations(List<GeoLocation> locations) {
		this.excludedLocations = locations;
	}

}
