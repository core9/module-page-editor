package io.core9.editor;

public interface GitHandler {

	void pull();

	void init();

	void push();

	void setUser(String user);

	void setPassword(String password);

}
