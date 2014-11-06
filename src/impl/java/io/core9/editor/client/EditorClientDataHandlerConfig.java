package io.core9.editor.client;

import io.core9.plugin.widgets.datahandler.DataHandlerDefaultConfig;

public class EditorClientDataHandlerConfig extends DataHandlerDefaultConfig {

	private String profile;
	private String to;
	private String subject;
	private String from;
	private String template;

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

}
