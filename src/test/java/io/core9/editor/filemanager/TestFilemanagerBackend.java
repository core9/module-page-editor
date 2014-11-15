package io.core9.editor.filemanager;

import org.junit.Test;

public class TestFilemanagerBackend {




	public FileManager createFileManagerBackend() throws Exception{
		return new FileManagerImpl("data/filemanager");
	}

	@Test
	public void test_get_node() throws Exception{

		FileManager fm = createFileManagerBackend();



		FileManagerRequest request = new FileManagerRequest();
		request.setOperation("get_node");
		request.setId("assets%2Fbootstrap");
		String result = fm.action(request);

		System.out.println(result);


/*

[
	{
		text: "css",
		children: true,
		id: "assets/bootstrap/css",
		icon: "folder"
	},
	{
		text: "fonts",
		children: true,
		id: "assets/bootstrap/fonts",
		icon: "folder"
	},
	{
		text: "js",
		children: true,
		id: "assets/bootstrap/js",
		icon: "folder"
	}
]
 *
 */

	}
}
