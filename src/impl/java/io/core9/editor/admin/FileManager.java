package io.core9.editor.admin;

import java.io.IOException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

public interface FileManager {


	JSONObject data(String id) throws IOException;

	JSONObject create(String id, String name, boolean mkdir) throws IOException;

	JSONObject rename(String id, String name) throws IOException;

	JSONObject remove(String id) throws IOException;

	JSONObject move(String id, String parent) throws IOException;

	JSONObject copy(String id, String parent) throws IOException;


	String action(FileManagerRequest request) throws IOException;

	void clear() throws IOException, CouldNotCreateDirectory;

	JSONArray lst(String id, boolean withRoot) throws IOException;

	String save(String id, String content) throws IOException;


}
