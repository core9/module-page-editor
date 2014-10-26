package io.core9.editor;

import java.io.File;

import net.minidev.json.JSONObject;

public interface BlockData {

	void addFile(File file);

	String getFilePath();

	void setPosition(int i);

	void save(String updateDirectory);

	File getFile();

	void setData(JSONObject data);

	void setDataDirectory(String dataDirectory);

}
