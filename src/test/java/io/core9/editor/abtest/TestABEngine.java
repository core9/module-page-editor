package io.core9.editor.abtest;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.abtest.entities.ABDay;
import io.core9.editor.abtest.entities.ABDayImpl;
import io.core9.editor.abtest.entities.ABTest;
import io.core9.editor.abtest.entities.ABTestImpl;
import io.core9.editor.abtest.entities.ABTimeRange;
import io.core9.editor.abtest.entities.GeoLocation;
import io.core9.editor.client.ClientRepositoryImpl;

import org.junit.After;
import org.junit.Test;

public class TestABEngine {

	private static final String pathPrefix = "data/test-editor-variations";
	private AssetsManager assetsManager;
	private EditorRequest request;
	@SuppressWarnings("unused")
	private String clientId = "9a8eccd84f9c40c791281139a87da7b645f25fab";
	private ClientRepositoryImpl clientRepository;
	private String httpsRepositoryUrl = "https://github.com/jessec/site-core9.git";
	private TestManagerImpl testManager;

	private void setupWorkingDirectory() {
		setUpRequest();
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
		assetsManager.createWorkingDirectory();
		assertTrue(assetsManager.checkWorkingDirectory());
	}

	private void setUpRequest() {
		clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("easydrain.localhost", "easydrain");
		request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://easydrain.localhost:8080/p/scraper/nl");
	}

	@After
	public void cleanup() {

		ClientRepositoryImpl clientRepository = new ClientRepositoryImpl();
		clientRepository.addDomain("www.easydrain.nl", "easydrain");
		clientRepository.addDomain("easydrain.localhost", "easydrain");
		EditorRequest request = new EditorRequestImpl();
		request.setClientRepository(clientRepository);
		request.setAbsoluteUrl("http://easydrain.localhost:8080/p/scraper/nl");
		AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.deleteWorkingDirectory();
		assertFalse(assetsManager.checkWorkingDirectory());
	}



	public void setUpSiteEnvironment(){
		setupWorkingDirectory();
		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		assetsManager.createSiteDirectory();
		assetsManager.clonePublicSiteFromGit(httpsRepositoryUrl);
	}

	public void createABTestVariationDirs(){
		setUpSiteEnvironment();

		String path = assetsManager.getPageTemplatePath();
		String docRoot = assetsManager.getSiteDirectory();

		TestManager testManager = new TestManagerImpl();
		String rootDir = path.replace("template.html", "");
		testManager.setTestRootDir(rootDir);
		testManager.setSiteRootDir(docRoot);
		testManager.createVariationDirectories();

	}

	@Test
	public void testCreateABTestVariationDirs(){
		setUpSiteEnvironment();

		String path = assetsManager.getPageTemplatePath();
		String docRoot = assetsManager.getSiteDirectory();

		testManager = new TestManagerImpl();
		String rootDir = path.replace("template.html", "");
		testManager.setTestRootDir(rootDir);
		testManager.setSiteRootDir(docRoot);
		testManager.createVariationDirectories();

	}

	private ABTest getStandardTest(){
		ABTest abTest = new ABTestImpl();
		abTest.setName("name");
		abTest.setDomain("easydrain.localhost");
		abTest.setPath("/p/scraper/nl");
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
		TestProperties testProperties = new TestPropertiesImpl();

		List<String> propertyOrder = new ArrayList<>();
		propertyOrder.add(TestProperty.VERSION.getCode());
		propertyOrder.add(TestProperty.USER_AGENT.getCode());
		propertyOrder.add(TestProperty.GEO_LOCATION.getCode());
		propertyOrder.add(TestProperty.REQUEST.getCode());
		propertyOrder.add(TestProperty.PERIOD.getCode());
		propertyOrder.add(TestProperty.PERCENTAGE.getCode());

		testProperties.setOrder(propertyOrder);

		abTest.setTestProperties(testProperties);

		return abTest;
	}

	@Test
	public void testAddABtest(){
		createABTestVariationDirs();

		ABTest abTest = getStandardTest();

		testManager.addTest(abTest);
		testManager.saveTests();
	}

}
