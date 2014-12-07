package io.core9.editor.abtest;

import java.util.List;

public interface ABStorage {

	void addTest(ABTest abTest);

	List<ABTest> getTests();


}
