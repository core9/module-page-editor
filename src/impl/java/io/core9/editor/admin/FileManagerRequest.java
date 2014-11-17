package io.core9.editor.admin;

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
	private String parent;

	public void setName(String name) throws Exception {
		if(name == null) return;
		this.name = URLDecoder.decode(name, "UTF-8");
		this.name = this.name.replace(" ", "-");
		if (!isValidName(this.name)) {
			throw new Exception("Invalid name: " + this.name);
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
		if (this.id.equals("#") || this.id == null){
			this.id = "/";
			return;
		}
		this.id = this.id.replace(" ", "-");
		if (!isValidFilename(this.id)) {
			throw new Exception("Invalid filename: " + this.id);
		}
	}

	public void setParent(String parent) throws Exception {

		if (parent == null){
			this.parent = "/";
			return;
		}
		if (parent.equals("#")){
			this.parent = "/";
			return;
		}
		this.parent = URLDecoder.decode(parent, "UTF-8");
		this.parent = this.parent.replace(" ", "-");
		if (!isValidFilename(this.parent)) {
			throw new Exception("Invalid parent filename: " + this.parent);
		}
	}

	public void setType(String type) throws Exception {
		if(type == null) return;
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

	public static boolean isValidFilename(String id) {
		if(id == null) return false;
		if(id.equals("/")) return true;
		id = id.replace("/", "");
		return isValidName(id);
	}

	public static boolean isValidName(String s) {
		if(s == null)return false;
		boolean result = s.matches("^[a-zA-Z0-9-.]+$") && s.length() != 0;
		return result;
	}

	public String getParent() {
		return parent;
	}



}
