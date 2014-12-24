package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SuppressWarnings("unused")
public class TestManagerImpl implements TestManager {


	private String activeVariationDirName = "variations/active";
	private String availableVariationDirName = "variations/available";

	private String testRootDir;
	private String siteRootDir;
	private File activeVariationDir;
	private File availableVariationDir;
	private Map<String, ABTest> abTestRegistry = new HashMap<String, ABTest>();

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

	}

}
