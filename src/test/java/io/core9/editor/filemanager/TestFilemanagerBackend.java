package io.core9.editor.filemanager;

import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.After;
import org.junit.Test;

public class TestFilemanagerBackend {

	public FileManager createFileManagerBackend() throws Exception {
		return new FileManagerImpl("data/filemanager");
	}

	@After
	public void cleanTestDir() throws CouldNotCreateDirectory, IOException{
		new FileManagerImpl("data/filemanager").clear();
	}


	@Test
	public void test_get_node() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request3 = new FileManagerRequest();
		request3.setOperation("create_node");
		request3.setId("New-node-2");
		request3.setName("New+dir-2");
		request3.setType("default");
		String result3 = fm.action(request3);
		String expected3 = "{\"id\":\"\\/New-node-2\\/New-dir-2\"}";
		assertTrue(expected3.equals(result3));



		// /?operation=get_node&id=New-node-2
		FileManagerRequest request4 = new FileManagerRequest();
		request4.setOperation("get_node");
		request4.setId("New-node-2");
		String result4 = fm.action(request4);
		String expected4 = "[{\"children\":false,\"icon\":\"file file-noext\",\"text\":\"New-node\",\"id\":\"\\/New-node-2\\/New-node\",\"type\":\"file\"},{\"children\":true,\"icon\":\"folder\",\"text\":\"New-dir-2\",\"id\":\"\\/New-node-2\\/New-dir-2\"}]";
		assertTrue(expected4.equals(result4));


		// /?operation=get_node&id=New-node-2
		FileManagerRequest request5 = new FileManagerRequest();
		request5.setOperation("get_node");
		request5.setId("#");
		String result5 = fm.action(request5);
		String expected5 = "[{\"children\":[{\"children\":true,\"icon\":\"folder\",\"text\":\"New-node-2\",\"id\":\"\\/New-node-2\"}],\"icon\":\"folder\",\"text\":\"filemanager\",\"id\":\"\\/\",\"state\":{\"opened\":true,\"disabled\":true}}]";
		assertTrue(expected5.equals(result5));


	}

	@Test
	public void test_create_node_directory() throws Exception {

		FileManager fm = createFileManagerBackend();
		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
	}

	@Test
	public void test_get_content() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));


		// /?operation=get_content&id=New%20node
		FileManagerRequest request3 = new FileManagerRequest();
		request3.setOperation("get_content");
		request3.setId("New-node-2");
		String result3 = fm.action(request3);
		String expected3 = "{\"type\":\"folder\",\"content\":\"New-node-2\"}";
		assertTrue(expected3.equals(result3));

	}

	@Test
	public void test_rename_node() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));


		// /?operation=rename_node&id=New-node-2%2FNew+node&text=test
		FileManagerRequest request3 = new FileManagerRequest();
		request3.setOperation("rename_node");
		request3.setId("New-node-2%2FNew+node");
		request3.setName("test");
		String result3 = fm.action(request3);
		String expected3 = "{\"id\":\"\\/New-node-2\\/test\"}";
		assertTrue(expected3.equals(result3));

	}


	@Test
	public void test_create_node_file() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));

	}

	@Test
	public void test_delete_node_file_and_directory() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));

		// /?operation=delete_node&id=New-node-2%2FNew-node
		FileManagerRequest request3 = new FileManagerRequest();
		request3.setOperation("delete_node");
		request3.setId("New-node-2%2FNew-node");
		String result3 = fm.action(request3);
		String expected3 = "{\"status\":\"OK\"}";
		assertTrue(expected3.equals(result3));


		// /?operation=delete_node&id=New-node-2%2FNew-node
		FileManagerRequest request4 = new FileManagerRequest();
		request4.setOperation("delete_node");
		request4.setId("New-node-2");
		String result4 = fm.action(request4);
		String expected4 = "{\"status\":\"OK\"}";
		assertTrue(expected4.equals(result4));


	}

	@Test
	public void test_copy_node_file() throws Exception {
		FileManager fm = createFileManagerBackend();

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("create_node");
		request.setId("%2F");
		request.setName("New-node-2");
		request.setType("default");
		String result = fm.action(request);
		String expected = "{\"id\":\"\\/New-node-2\"}";
		assertTrue(expected.equals(result));
		System.out.println(result);

		// /?operation=create_node&type=file&id=New-node-2&text=New+node
		FileManagerRequest request2 = new FileManagerRequest();
		request2.setOperation("create_node");
		request2.setId("New-node-2");
		request2.setName("New+node");
		request2.setType("file");
		String result2 = fm.action(request2);
		String expected2 = "{\"id\":\"\\/New-node-2\\/New-node\"}";
		assertTrue(expected2.equals(result2));

		// /?operation=create_node&type=default&id=%2F&text=New+node+2
		FileManagerRequest request3 = new FileManagerRequest();
		request3.setOperation("create_node");
		request3.setId("%2F");
		request3.setName("New-node-3");
		request3.setType("default");
		String result3 = fm.action(request3);
		String expected3 = "{\"id\":\"\\/New-node-3\"}";
		assertTrue(expected3.equals(result3));
		System.out.println(result3);


		// /?operation=copy_node&id=New-node-2%2FNew-node&parent=New-node-3
		FileManagerRequest request4 = new FileManagerRequest();
		request4.setOperation("copy_node");
		request4.setId("New-node-2%2FNew-node");
		request4.setParent("New-node-3");
		String result4 = fm.action(request4);
		String expected4 = "{\"id\":\"\\/New-node-3\\/New-node\"}";
		assertTrue(expected4.equals(result4));
		System.out.println(result4);

	}


}
