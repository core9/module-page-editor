package io.core9.editor.filemanager;

import java.io.IOException;

import net.minidev.json.JSONObject;

public interface FileManager {

	String[] lst(String id, String with_root);

	JSONObject data(String id) throws IOException;

	JSONObject create(String id, String name, boolean mkdir) throws IOException;

	void rename(String id, String name);

	JSONObject remove(String id) throws IOException;

	void move(String id, String parent);

	JSONObject copy(String id, String parent) throws IOException;


	String action(FileManagerRequest request) throws IOException;

	void clear() throws IOException, CouldNotCreateDirectory;


}
