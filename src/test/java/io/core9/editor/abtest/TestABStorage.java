package io.core9.editor.abtest;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestABStorage {

	private ABStorage storage;

	@Test
	public void testCreateTest(){
		ABTestImpl abTest = getStandardTest();
		storage.addTest(abTest);
		assertTrue(storage.getTests().size() == 1);
	}

	@Test
	public void testGetJsonTest() throws JsonParseException, JsonMappingException, IOException{
		ABTestImpl abTest = getStandardTest();
		String json = abTest.toString();
		System.out.println(json);


		ObjectMapper mapper = new ObjectMapper();
		Object res = mapper.readValue(json, Object.class);


		System.out.println(res);
	}


	@Test
	public void testCreateVariationsForTest(){
		ABTestImpl abTest = getStandardTest();

		ABTestImpl abTest1 = getStandardTest();

		abTest.addTest(abTest1);

		storage.addTest(abTest);
		assertTrue(storage.getTests().size() == 1);
	}

	@Test
	public void testGetTestIdentifier(){
		ABTestRequest request = getStandardABRequest();


		ABTestImpl abTest = getStandardTest();



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

	private ABTestImpl getStandardTest(){
		ABTestImpl abTest = new ABTestImpl();
		abTest.setName("name");
		abTest.setDomain("easydrain.localhost");
		abTest.setPath("/path");
		Date startDate = new Date();
		Date endDate = new Date();
		abTest.setStartDate(startDate);
		abTest.setEndDate(endDate);
		List<ABDay> timeRangeOnDays = new ArrayList<ABDay>();
		ABDay abDay = new ABDayImpl(new Date());
		List<ABTimeRange> timeRangesOnDay = new ArrayList<>();
		abDay.setTimeRangesOnDay(timeRangesOnDay);
		abTest.setTimeRange(timeRangeOnDays);
		List<GeoLocation> locations = new ArrayList<>();
		abTest.setIncludedGeoLocations(locations);
		abTest.setExcludedGeoLocations(locations);

		ABTestImpl abTest2 = new ABTestImpl();
		abTest2.setName("name");
		abTest2.setDomain("easydrain.localhost");
		abTest2.setPath("/path");
		abTest.addTest(abTest2);

		ABTestImpl abTest3 = new ABTestImpl();
		abTest3.setName("name");
		abTest3.setDomain("easydrain.localhost");
		abTest3.setPath("/path");

		ABTestImpl abTest31 = new ABTestImpl();
		abTest31.setName("name");
		abTest31.setDomain("easydrain.localhost");
		abTest31.setPath("/path");
		abTest3.addTest(abTest31);
		abTest.addTest(abTest3);

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
