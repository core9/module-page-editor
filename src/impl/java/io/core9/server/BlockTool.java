package io.core9.server;

import net.minidev.json.JSONObject;

public interface BlockTool {


	String getResponse();

	void setData(String prefix, JSONObject data);

}