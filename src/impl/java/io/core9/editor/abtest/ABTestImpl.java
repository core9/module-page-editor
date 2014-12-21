package io.core9.editor.abtest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class ABTestImpl implements ABTest {


	private boolean active;
	private String name;
	private String domain;
	private String path;
	private Date startDate;
	private Date endDate;
	private List<ABDay> timeRange = new ArrayList<>();
	private List<GeoLocation> includedLocations = new ArrayList<>();
	private List<GeoLocation> excludedLocations = new ArrayList<>();
	private List<ABTest> testRegistry = new ArrayList<>();



	@Override
	public void setDomain(String domain) {
		this.domain = domain;
	}

	@Override
	public String getDomain() {
		return domain;
	}

	@Override
	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String getPath() {
		return path;
	}



	@Override
	public void setTimeRange(List<ABDay> timeRange) {
		this.timeRange = timeRange;
	}

	@Override
	public List<ABDay> getTimeRange() {
		return timeRange;
	}

	@Override
	public void setIncludedGeoLocations(List<GeoLocation> locations) {
		this.setIncludedLocations(locations);
	}

	@Override
	public void setExcludedGeoLocations(List<GeoLocation> locations) {
		this.setExcludedLocations(locations);
	}

	@Override
	public void addTest(ABTest abTest) {
		testRegistry.add(abTest);
	}

	@Override
	public List<ABTest> getTests(){
		return testRegistry;
	}

	@Override
	public String toString(){

		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = null;
		try {
			json = ow.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return  json;
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	@Override
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	@Override
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	@Override
	public List<GeoLocation> getIncludedLocations() {
		return includedLocations;
	}

	@Override
	public void setIncludedLocations(List<GeoLocation> includedLocations) {
		this.includedLocations = includedLocations;
	}

	@Override
	public List<GeoLocation> getExcludedLocations() {
		return excludedLocations;
	}

	@Override
	public void setExcludedLocations(List<GeoLocation> excludedLocations) {
		this.excludedLocations = excludedLocations;
	}

}
