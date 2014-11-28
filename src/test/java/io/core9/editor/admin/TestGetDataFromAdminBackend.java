package io.core9.editor.admin;

import static org.junit.Assert.assertTrue;

import org.junit.Test;


// for this test to work everything has to be running..

public class TestGetDataFromAdminBackend {




	@Test
	public void testGetContent(){

		String content = getContentOfFile("/menus/steps/steps.json", "menus", "steps.json");
		System.out.println(content);
		assertTrue("[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/menus/steps/step-1.js\"}]".equals(content));
	}


	@Test
	public void testGetJavascriptStepFile(){

		String content = getContentOfFile("/menus/steps/step-1.js", "menus", "step-1.js");
		System.out.println(content);
	}


	private String getJavascriptStepFile(String dynamicContentType) {

		return null;
	}

	private String getContentOfFile(String requestPath, String dynamicContentType, String fileName) {
		String content = "";
		if(requestPath.endsWith("steps.json")){
			// is step json
			content = "[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/"+dynamicContentType+"/steps/step-1.js\"}]";
		}else if(requestPath.endsWith(".js")){
			// is js step file
			content = getJavascriptStepFile(dynamicContentType);
		}else {
			// is json data file
			content = "data";
		}

		return content;
	}




}
