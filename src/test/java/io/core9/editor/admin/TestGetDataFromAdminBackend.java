package io.core9.editor.admin;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.Test;


// for this test to work everything has to be running..

public class TestGetDataFromAdminBackend {





	@Test
	public void testGetStepJson() throws Exception{

		String content = AdminConnector.getContentOfFile("/menus/steps/steps.json", "menus", "steps.json", null);
		System.out.println(content);
		assertTrue("[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/menus/steps/step-1.js\"}]".equals(content));
	}


	@Test
	public void testGetJavascriptStepFile() throws Exception{

		String content = AdminConnector.getContentOfFile("/menus/steps/step-1.js", "menus", "step-1.js", null);
		System.out.println(content);
	}

	@Test
	public void testGetJsonDataForDynamicContentType() throws Exception{

		String content = AdminConnector.getContentOfFile("/menus/data/menus.json", "menus", "menus.json", "1JEYT4U07H0TI");
		System.out.println(content);
	}

	@Test
	public void testPutJsonDataForDynamicContentType() throws Exception{

		String data = "{\"\":\"\",\"data\":\"{\"meta\":{\"absolute-url\":\"\",\"state\":\"edit\",\"block\":0,\"type\":\"menus\",\"template\":\"\",\"contentid\":\"1JEYT4U07H0TI\"},\"editor\":{\"menus\":[{\"heading\":\"test\",\"items\":[{\"link\":\"test\",\"title\":\"test\"}]},{\"heading\":\"asdfaasd\",\"items\":[{\"link\":\"sdfa\",\"title\":\"asdfddd\"}]}]}}\",\"id\":\"112\"}";

		@SuppressWarnings("unused")
		JSONObject json = (JSONObject) JSONValue.parse(data);


		Map<String, Object> postData = null;
		String content = AdminConnector.updateDynamicContentType(postData);
		System.out.println(content);
	}




}
