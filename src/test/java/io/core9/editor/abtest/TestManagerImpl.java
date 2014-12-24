package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;


@SuppressWarnings("unused")
public class TestManagerImpl implements TestManager {


	private String activeVariationDirName = "variations/active";
	private String availableVariationDirName = "variations/available";

	private String testRootDir;
	private String siteRootDir;
	private File activeVariationDir;
	private File availableVariationDir;
	private Map<String, ABTest> abTestRegistry = new HashMap<String, ABTest>();
	private TestProperties testProperties;

	@Override
	public void setTestRootDir(String testRootDir) {
		this.testRootDir = testRootDir;
		this.activeVariationDir = new File(testRootDir + activeVariationDirName);
		this.availableVariationDir = new File(testRootDir + availableVariationDirName);
	}

	@Override
	public void createVariationDirectories() {
		if(!activeVariationDir.exists()){
			activeVariationDir.mkdirs();
		}
		if(!availableVariationDir.exists()){
			availableVariationDir.mkdirs();
		}
	}

	@Override
	public void setSiteRootDir(String siteRootDir) {
		this.siteRootDir = siteRootDir;
	}

	@Override
	public void addTest(ABTest abTest) {
		abTestRegistry.put(abTest.getName(), abTest);
	}

	@Override
	public void saveTests() {

		saveTestProperties();

		for(Entry<String, ABTest> test : abTestRegistry.entrySet()){

			saveFile(availableVariationDir + "/test." + test.getKey() + ".json", test.getValue().toString());
		}



	}

	private void saveTestProperties() {
		String json = "";

		List<String> props = testProperties.getPropertyOrder();

		json = JSONValue.toJSONString(props);

		saveFile(availableVariationDir + "/properties.json", json);
	}

	private void saveFile(String filename, String json) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filename, "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		writer.println(json);
		writer.close();
	}

	@Override
	public void setTestProperties(TestProperties testProperties) {
		this.testProperties = testProperties;
	}

}
