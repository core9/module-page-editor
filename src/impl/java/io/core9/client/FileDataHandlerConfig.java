package io.core9.client;

import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.Core9GlobalConfiguration;
import io.core9.plugin.widgets.datahandler.DataHandlerDefaultConfig;
import io.core9.plugin.widgets.datahandler.DataHandlerGlobal;

public class FileDataHandlerConfig extends DataHandlerDefaultConfig {

	@Core9GlobalConfiguration(type = "file")
	private DataHandlerGlobal<String> clientID;

	/**
	 * @return the menuName
	 */
	public DataHandlerGlobal<String> getClientID() {
		return clientID;
	}

	/**
	 * Return the menuID from a global
	 * @param request
	 * @return
	 */
	public String getClientId(Request request) {
		if(clientID.isGlobal()) {
			return request.getContext(this.getComponentName() + ".clientID", clientID.getValue());
		}
		return clientID.getValue();
	}

	/**
	 * @param menuName the menuName to set
	 */
	public void setClientId(DataHandlerGlobal<String> clientID) {
		this.clientID = clientID;
	}

}
