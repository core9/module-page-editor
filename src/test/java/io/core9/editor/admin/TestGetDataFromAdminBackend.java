package io.core9.editor.admin;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.junit.Test;


// for this test to work everything has to be running..

public class TestGetDataFromAdminBackend {

	private final String USER_AGENT = "Mozilla/5.0";



	@Test
	public void testGetStepJson() throws Exception{

		String content = getContentOfFile("/menus/steps/steps.json", "menus", "steps.json", null);
		System.out.println(content);
		assertTrue("[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/menus/steps/step-1.js\"}]".equals(content));
	}


	@Test
	public void testGetJavascriptStepFile() throws Exception{

		String content = getContentOfFile("/menus/steps/step-1.js", "menus", "step-1.js", null);
		System.out.println(content);
	}

	@Test
	public void testGetJsonDataForDynamicContentType() throws Exception{

		String content = getContentOfFile("/menus/data/menus.json", "menus", "menus.json", "1JEYT4U07H0TI");
		System.out.println(content);
	}




	private String getJavascriptStepFile(String dynamicContentType) throws Exception {
		Map<String, JSONObject> dynamicContentTypeSchemas = getDynamicContentTypeSchemas();
		JSONObject schema = dynamicContentTypeSchemas.get(dynamicContentType);
		String result = "var config = {data : 'dynamic-blocks/"+dynamicContentType+"/data/"+dynamicContentType+".json', schema : "+schema+"}function init(step) {Wizard.run(step, config);}";
		return result;
	}




	private Map<String, JSONObject> getDynamicContentTypeSchemas() throws Exception {
		Map<String, JSONObject> result = new HashMap<String, JSONObject>();
		String url = "http://easydrain.localhost:8080/admin/config/content";
		String rawContentTypes = sendGet(url);


		JSONArray rawTypes = (JSONArray) JSONValue.parseWithException(rawContentTypes);

		for(Object type : rawTypes){
			JSONObject json = (JSONObject) type;
			JSONObject schema = (JSONObject) json.get("schema");
			System.out.println(schema);
			result.put(schema.getAsString("title"), schema);
		}

		return result;
	}

	private String getJsonDataForDynamicContentType(String dynamicContentType, String id) throws Exception {
		String url = "http://easydrain.localhost:8080/admin/content/menus/1JEYT4U07H0TI";
		String rawContentTypes = sendGet(url);
		JSONObject rawTypes = (JSONObject) JSONValue.parseWithException(rawContentTypes);

		String result = rawTypes.getAsString(dynamicContentType);

		return result;
	}


	private String sendGet(String url) throws Exception {


		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(
		        new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		//print result
		System.out.println(response.toString());
		return response.toString();

	}

	private String getContentOfFile(String requestPath, String dynamicContentType, String fileName, String id) throws Exception {
		String content = "";
		if(requestPath.endsWith("steps.json")){
			// is step json
			content = "[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/"+dynamicContentType+"/steps/step-1.js\"}]";
		}else if(requestPath.endsWith(".js")){
			// is js step file
			content = getJavascriptStepFile(dynamicContentType);
		}else {
			// is json data file
			content = getJsonDataForDynamicContentType(dynamicContentType, id);
		}

		return content;
	}







}
