package io.core9.editor.abtest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestABStorage {

	private ABStorage storage;

	@Test
	public void testCreateTest(){
		ABTest abTest = new ABTestImpl("name");
		storage.addTest(abTest);
		assertTrue(storage.getTests().size() == 1);
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
