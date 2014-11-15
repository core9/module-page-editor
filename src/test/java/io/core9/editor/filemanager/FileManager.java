package io.core9.editor.filemanager;

public interface FileManager {

	String[] lst(String id, String with_root);

	void data(String id);

	void create(String id, String name, boolean mkdir);

	void rename(String id, String name);

	void remove(String id);

	void move(String id, String par);

	void copy(String id, String par);


	String action(FileManagerRequest request);


}
