package io.core9.editor;

import java.io.File;

public interface BlockData {

	void addFile(File file);

	String getFilePath();

	void setPosition(int i);

	void save(String updateDirectory);

	File getFile();

}
