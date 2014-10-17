package io.core9.editor;

public interface ClientRepository {


	String getClientForDomain(String domain);

	void addDomain(String domain, String client);


}
