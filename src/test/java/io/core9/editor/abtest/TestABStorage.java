package io.core9.editor.abtest;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestABStorage {

	private ABStorage storage;

	@Test
	public void testCreateTest(){
		ABTest abTest = getStandardTest();
		storage.addTest(abTest);
		assertTrue(storage.getTests().size() == 1);
	}

	@Test
	public void testCreateVariationsForTest(){
		ABTest abTest = getStandardTest();

		ABTest abTest1 = getStandardTest();

		abTest.addTest(abTest1);

		storage.addTest(abTest);
		assertTrue(storage.getTests().size() == 1);
	}

	@Test
	public void testGetTestIdentifier(){
		ABTestRequest request = getStandardABRequest();


		ABTest abTest = getStandardTest();



		storage.addTest(abTest);

		String abIdentifier = storage.getIdentifier(request);

		System.out.println(abIdentifier);

	}



	private ABTestRequest getStandardABRequest(){
		ABTestRequest abTestRequest = new ABTestRequestImpl();
		abTestRequest.setProtocol("http");
		abTestRequest.setDomain("easydrain.localhost");
		abTestRequest.setPath("/path");
		abTestRequest.setTimeStamp(System.currentTimeMillis() / 1000L);
		abTestRequest.setABSessionId("ab-session-id");
		GeoLocation location = new GeoLocationImpl("geolocation name");
		location.setIpAddress("176.45.34.22");
		abTestRequest.setGeoLocation(location);
		return abTestRequest;
	}

	private ABTest getStandardTest(){
		ABTest abTest = new ABTestImpl("name");
		abTest.setDomain("easydrain.localhost");
		abTest.setPath("/path");
		Date startDate = new Date();
		Date endDate = new Date();
		abTest.setDateRange(startDate, endDate);
		List<ABDay> timeRangeOnDays = new ArrayList<ABDay>();
		ABDay abDay = new ABDayImpl(new Date());
		List<ABTimeRange> timeRangesOnDay = new ArrayList<>();
		abDay.setTimeRangesOnDay(timeRangesOnDay);
		abTest.setTimeRange(timeRangeOnDays);
		List<GeoLocation> locations = new ArrayList<>();
		abTest.setIncludedGeoLocations(locations);
		abTest.setExcludedGeoLocations(locations);
		return abTest;
	}


	@After
	public void clearStorage(){
		ABStorageImpl.clear();
	}

	@Before
	public void setupStorage(){
		storage = ABStorageImpl.getInstance();
	}
}
