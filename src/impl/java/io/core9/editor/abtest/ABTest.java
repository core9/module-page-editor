package io.core9.editor.abtest;

import java.util.Date;
import java.util.List;

public interface ABTest {

	void setDomain(String domain);

	void setPath(String path);

	void setDateRange(Date startDate, Date endDate);

	void setTimeRange(List<ABDay> timeRange);

	void setIncludedGeoLocations(List<GeoLocation> locations);

	void setExcludedGeoLocations(List<GeoLocation> locations);

	void addVariation(Variation variation);

}
