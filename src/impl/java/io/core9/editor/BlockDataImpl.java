package io.core9.editor;

import java.io.File;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class BlockDataImpl implements BlockData {

	private String file;
	private int position;
	private JSONObject jsonData;
	private String dataDirectory;

	@Override
	public void addFile(File file) {
		this.file = file.getPath();
	}

	@Override
	public String getFilePath() {
		return file;
	}

	@Override
	public void setPosition(int i) {
		position = i;
	}

	@Override
	public void save(String updateDirectory) {
		System.out.println("");
		if (file == null || jsonData != null) {
			file = getFileNameFromJsonData();
		}
		String content = "";
		try {
			 content = updateMetaPosition(position, FileUtils.readPathToString(new File(file).toPath()));
		} catch (NullPointerException e) {
			content = jsonData.toJSONString();
		}

		String fileName = updatePositionInFileName(position, getFileNameFromFilePath(file));

		FileUtils.writeToFile(new File(new File(updateDirectory), fileName).toPath(), content);
	}

	private String getFileNameFromFilePath(String fileName) {
		return new File(fileName).getName();
	}

	private String getFileNameFromJsonData() {
		JSONObject data = (JSONObject) jsonData.get("meta");
		String fileName = dataDirectory + File.separator + "block-" + data.getAsString("block") + "-type-" + data.getAsString("type") + ".json";
		return fileName;
	}

	private String updateMetaPosition(int pos, String content) {
		JSONObject jsonObj = getJsonDataFromContent(content);
		JSONObject data = (JSONObject) jsonObj.get("meta");
		data.put("block", Integer.toString(pos));
		jsonObj.put("meta", data);
		return jsonObj.toJSONString();
	}

	private JSONObject getJsonDataFromContent(String content) {
		return (JSONObject) JSONValue.parse(content);
	}

	private String updatePositionInFileName(int pos, String name) {
		String[] parts = name.split("-");
		return parts[0] + "-" + Integer.toString(pos) + "-" + parts[2] + "-" + parts[3];
	}



	@Override
	public void setData(JSONObject data) {
		this.jsonData = data;
	}

	@Override
	public void setDataDirectory(String dataDirectory) {
		this.dataDirectory = dataDirectory;
	}

}
