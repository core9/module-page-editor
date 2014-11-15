package io.core9.editor.filemanager;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestFilemanagerBackend {

	public FileManager createFileManagerBackend() throws Exception {
		return new FileManagerImpl("data/filemanager");
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
		request2.setId("%2F");
		request2.setName("New-node-2");
		request2.setType("default");
		fm.action(request2);

		/*
		 * {
id: "New-node-2/New node"
}
		 */
	}

}
