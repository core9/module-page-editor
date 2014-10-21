package io.core9.editor;

import java.util.List;

public interface ClientRepository {


	String getClientForDomain(String domain);

	void addDomain(String domain, String client);

	void addSiteRepository(String client, String repository);

	void addBlockRepository(String client, String repository);

	String getSiteRepository(String client);

	List<String> getBlockRepository(String client);

	String getPage(String hostname);

	void addPage(String domain, String page);


}
