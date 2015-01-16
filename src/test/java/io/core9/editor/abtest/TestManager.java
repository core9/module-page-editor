package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;

public interface TestManager {

	void setTestRootDir(String rootDir);

	void createVariationDirectories();

	void setSiteRootDir(String docRoot);

	void addTest(ABTest abTest);

	void saveTests();

	void setTestProperties(TestProperties testProperties);

}
