package io.core9.editor;

import java.io.File;

public interface BlockData {

	void addFile(File file);

	String getFile();

	void setPosition(int i);

	void save(String updateDirectory);

}
