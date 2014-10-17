package io.core9.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import io.core9.plugin.database.repository.AbstractCrudEntity;
import io.core9.plugin.database.repository.Collection;
import io.core9.plugin.database.repository.CrudEntity;

@Collection("configuration")
public class EditorClientProfile extends AbstractCrudEntity implements CrudEntity {

	private String host;
	private String name;
	private String configtype = "mailerprofile";
	private int port;
	private String type;
	private boolean authentication;
	private String username;
	private String password;

	public String getConfigtype() {
		return configtype;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isAuthentication() {
		return authentication;
	}

	public void setAuthentication(boolean authentication) {
		this.authentication = authentication;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Map<String, Object> retrieveDefaultQuery() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("configtype", "mailerprofile");
		return result;
	}

	public static Properties parseProperties(EditorClientProfile profile) {
		Properties result = new Properties();
		result.put("mail.smtp.host", profile.host);
		result.put("mail.smtp.auth", profile.authentication ? "true" : "false");
		result.put("mail.smtp.port", profile.port);
		result.put("mail.smtp.starttls.enable", "true");
		result.put("mail.smtp.socketFactory.port", "465");
		result.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		return result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
