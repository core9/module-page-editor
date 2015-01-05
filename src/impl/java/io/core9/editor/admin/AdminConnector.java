package io.core9.editor.admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class AdminConnector {


	private static final String USER_AGENT = "Mozilla/5.0";


	private static String getJavascriptStepFile(String dynamicContentType) throws Exception {
		Map<String, JSONObject> dynamicContentTypeSchemas = getDynamicContentTypeSchemas();
		JSONObject schema = dynamicContentTypeSchemas.get(dynamicContentType);
		String result = "var config = {\"data\" : \"dynamic-blocks/"+dynamicContentType+"/data/"+dynamicContentType+".json\", \"schema\" : "+schema+"}; function init(step) {Wizard.run(step, config);}";
		return result;
	}




	private static Map<String, JSONObject> getDynamicContentTypeSchemas() throws Exception {
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

	private static String getJsonDataForDynamicContentType(String dynamicContentType, String id) throws Exception {
		String url = "http://easydrain.localhost:8080/admin/content/menus/"  + id;
		String rawContentTypes = sendGet(url);
		JSONObject rawTypes = (JSONObject) JSONValue.parseWithException(rawContentTypes);

		JSONObject res = new JSONObject();

		Object result = rawTypes.get(dynamicContentType);

		res.put(dynamicContentType, result);

		return res.toJSONString();
	}


	private static String sendGet(String url) throws Exception {


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

	public static String getContentOfFile(String requestPath, String dynamicContentType, String fileName, String id) throws Exception {
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




	public static String updateDynamicContentType(Map<String, Object> postData) {
		// TODO Auto-generated method stub
		System.out.println(postData);

		String jsonStr = (String) postData.get("data");
		JSONObject json = new JSONObject();
		try {
			json = (JSONObject) JSONValue.parseWithException(jsonStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JSONObject meta = (JSONObject) json.get("meta");

		String contentId = meta.getAsString("contentid");
		String type = meta.getAsString("type");

		JSONObject editor = (JSONObject) json.get("editor");

		String str = json.toJSONString();

		System.out.println(str);


		editor.put("_id", contentId);
		editor.put("contenttype", type);

		//doc {"_id":"RHTWLCKT1VTX","title":"afsddsfdd","contenttype":"hierarchical"}

		// doc {"menus":[{"heading":"testdd","items":[{"link":"test","title":"test"}]},{"heading":"asdfaasd","items":[{"link":"sdfa","title":"asdfddd"}]}],"_id":"1JEYT4U07H0TI","contenttype":"menus"}
		// query {_id=1JEYT4U07H0TI, contenttype=menus}

		String url = "http://easydrain.localhost:8080/admin/content/"+type+"/" + contentId;
		//String url = "http://easydrain.localhost:8080/admin/content/" + contentId;
		return restPutClient(url, editor);
	}

	public static String restPutClient(String url, JSONObject data) {
        // example url : http://localhost:9898/data/1d3n71f13r.json
		HttpClient httpClient = HttpClientBuilder.create().build();
        StringBuilder result = new StringBuilder();
        try {
            HttpPut putRequest = new HttpPut(url);
            putRequest.addHeader("Content-Type", "application/json");
            putRequest.addHeader("Accept", "application/json");
/*            JSONObject keyArg = new JSONObject();
            keyArg.put("value1", newValue);
            keyArg.put("value2", newValue2);*/
            StringEntity input;
            try {
                input = new StringEntity(data.toString());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "success";
            }
            putRequest.setEntity(input);
            HttpResponse response = httpClient.execute(putRequest);
            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatusLine().getStatusCode());
            }
            System.out.println("data : ");
            System.out.println(data);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (response.getEntity().getContent())));
            String output;
            while ((output = br.readLine()) != null) {
                result.append(output);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }


}
