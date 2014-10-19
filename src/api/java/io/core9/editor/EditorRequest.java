package io.core9.editor;

import java.util.Map;


public interface EditorRequest {

	void setAbsoluteUrl(String absoluteUrl);

	String getPath();

	String getHost();

	String getAbsoluteUrl();

	String getClient();

	void setClientRepository(ClientRepository clientRepository);

	Map<String, String> getParams();


}
