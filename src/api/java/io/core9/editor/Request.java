package io.core9.editor;

public interface Request {

	void setAbsoluteUrl(String absoluteUrl);

	String getPath();

	String getHost();

	String getAbsoluteUrl();

	String getClient();

	void setClientRepository(ClientRepository clientRepository);

}
