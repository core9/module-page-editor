package io.core9.editor.filemanager;

import java.io.IOException;

import net.minidev.json.JSONObject;

public interface FileManager {

	String[] lst(String id, String with_root);

	void data(String id);

	JSONObject create(String id, String name, boolean mkdir) throws IOException;

	void rename(String id, String name);

	void remove(String id);

	void move(String id, String par);

	void copy(String id, String par);


	String action(FileManagerRequest request) throws IOException;


}
