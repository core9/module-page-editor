package io.core9.editor.filemanager;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileManagerRequest {

	private String operation;
	private String id;
	private String type;
	private String name;
	private List<String> operations = new ArrayList<String>(
		    Arrays.asList("get_node", "get_content", "create_node", "rename_node", "delete_node", "move_node", "copy_node"));

	public void setName(String name) throws Exception {
		this.name = URLDecoder.decode(name, "UTF-8");
		if (!isValidName(this.name)) {
			throw new Exception("Invalid name: " + type);
		}
	}

	public void setOperation(String operation) throws Exception {
		if(!operations.contains(operation)){
			throw new Exception("Invalid operation : " + operation);
		}
		this.operation = operation;
	}

	public void setId(String id) throws Exception {
		this.id = URLDecoder.decode(id, "UTF-8");
		if (this.id.equals("#") || this.id == null)
			this.id = "/";
		if (!isValidFilename(this.id)) {
			throw new Exception("Invalid filename: " + this.id);
		}
	}

	public void setType(String type) throws Exception {
		if (!isValidName(type)) {
			throw new Exception("Invalid name: " + type);
		}
		this.type = URLDecoder.decode(type, "UTF-8");
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getOperation() {
		return operation;
	}

	public String getType() {
		return type;
	}

	private boolean isValidFilename(String id) {
		return id.matches("[^-_.A-Za-z0-9]") && id.length() != 0;
	}

	private boolean isValidName(String s) {
		return s.matches("[-\\p{Alnum}]+") && s.length() != 0;
	}

}
