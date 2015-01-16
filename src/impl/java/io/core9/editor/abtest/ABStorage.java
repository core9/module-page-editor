package io.core9.editor.abtest;

import io.core9.editor.abtest.entities.ABTest;
import io.core9.editor.abtest.entities.ABTestRequest;

import java.util.List;

public interface ABStorage {

	void addTest(ABTest abTest);

	List<ABTest> getTests();

	String getIdentifier(ABTestRequest request);


}
