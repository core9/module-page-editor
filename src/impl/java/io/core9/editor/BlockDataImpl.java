package io.core9.editor;

import java.io.File;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

public class BlockDataImpl implements BlockData {

	private File file;
	private int position;

	@Override
	public void addFile(File file) {
		this.file = file;
	}

	@Override
	public String getFile() {
		return file.getPath();
	}

	@Override
	public void setPosition(int i) {
		position = i;
	}

	@Override
	public void save(String updateDirectory) {
		String content = updateMetaPosition(position, FileUtils.readPathToString(file.toPath()));
		String fileName = updatePositionInFileName(position, file.getName());

		FileUtils.writeToFile(new File (new File (updateDirectory), fileName).toPath(), content);
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

}
