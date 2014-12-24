package io.core9.editor.abtest.entities;

import io.core9.editor.abtest.TestProperties;

import java.util.Date;
import java.util.List;

public interface ABTest {

	void setDomain(String domain);

	void setPath(String path);


	void setTimeRange(List<ABDay> timeRange);

	void setIncludedGeoLocations(List<GeoLocation> locations);

	void setExcludedGeoLocations(List<GeoLocation> locations);

	void addTest(ABTest abTest);

	List<ABTest> getTests();


	String getPath();

	String getDomain();

	List<ABDay> getTimeRange();

	boolean isActive();

	void setActive(boolean active);

	String getName();

	void setName(String name);

	Date getStartDate();

	void setStartDate(Date startDate);

	Date getEndDate();

	void setEndDate(Date endDate);

	List<GeoLocation> getIncludedLocations();

	void setIncludedLocations(List<GeoLocation> includedLocations);

	List<GeoLocation> getExcludedLocations();

	void setExcludedLocations(List<GeoLocation> excludedLocations);

	void setTestProperties(TestProperties testProperties);


}
