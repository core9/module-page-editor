package io.core9.editor.admin;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import org.junit.Test;


// for this test to work everything has to be running..

public class TestGetDataFromAdminBackend {

	private final String USER_AGENT = "Mozilla/5.0";



	@Test
	public void testGetContent() throws Exception{

		String content = getContentOfFile("/menus/steps/steps.json", "menus", "steps.json");
		System.out.println(content);
		assertTrue("[{\"label\":\"Step 1\",\"file\":\"dynamic-blocks/menus/steps/step-1.js\"}]".equals(content));
	}


	@Test
	public void testGetJavascriptStepFile() throws Exception{

		String content = getContentOfFile("/menus/steps/step-1.js", "menus", "step-1.js");
		System.out.println(content);
	}


	private String getJavascriptStepFile(String dynamicContentType) throws Exception {

		Map<String, String> dynamicContentTypeSchemas = getDynamicContentTypeSchemas();

		return null;
	}




	private Map<String, String> getDynamicContentTypeSchemas() throws Exception {
		String url = "http://easydrain.localhost:8080/admin/config/content";
		String rawContentTypes = sendGet(url);
		return null;
	}


	private String sendGet(String url) throws Exception {

		//String url = "http://www.google.com/search?q=mkyong";

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

	private String getContentOfFile(String requestPath, String dynamicContentType, String fileName) throws Exception {
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
