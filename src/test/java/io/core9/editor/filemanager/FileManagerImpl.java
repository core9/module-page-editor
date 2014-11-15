package io.core9.editor.filemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONObject;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;

import scala.util.control.Exception;

@SuppressWarnings("unused")
public class FileManagerImpl implements FileManager {

	private String base;

	public FileManagerImpl(String base) throws CouldNotCreateDirectory, IOException {
		this.base = new File(base).getCanonicalPath();
		createBaseDir();
	}


	private void createBaseDir() throws CouldNotCreateDirectory {
		File baseDir = new File(base);
		if(!baseDir.exists()) baseDir.mkdirs();
		if(!baseDir.exists()) throw new CouldNotCreateDirectory(baseDir);
	}


	@Override
	public String[] lst(String id, String with_root) {
		return null;
	}
	@Override
	public void data(String id) {
	}
	@Override
	public JSONObject create(String id, String name, boolean mkdir) throws IOException {


		String dir = getAbsolutePathFromId(id);
		if(mkdir){
			new File(dir + File.separator + name).mkdirs();
		}else{
			new File(dir + File.separator + name).createNewFile();
		}

		JSONObject result = new JSONObject();
		result.put("id", getIdFromPath(dir + File.separator + name));

		return result;

	}


	private String getIdFromPath(String path)throws IOException  {
		path = real(path);
		path = path.substring(base.length());
		path = path.replace(File.separator, "/");
		if(path.length() == 0){
			return "/";
		}
		return path;
	}


	private String getAbsolutePathFromId(String id) throws IOException {
		return real(base + File.separator + id.replace("/", File.separator).trim());
	}


	private String real(String path) throws IOException {
		String temp = new File(path).getCanonicalPath();
		if(base != null && base.length() != 0) {
			if(temp.indexOf(base) == -1) throw new FileNotFoundException("Path is not inside base (" + base + "): " + temp);
		}
		return temp;
	}





	@Override
	public void rename(String id, String name) {
	}
	@Override
	public void remove(String id) {
	}
	@Override
	public void move(String id, String par) {
	}
	@Override
	public void copy(String id, String par) {

	}

	@Override
	public String action(FileManagerRequest req) throws IOException{
		Object result = "";
		switch (req.getOperation())
		{
		case "create_node":
			result = this.create(req.getId(), req.getName(), (!req.getType().equals("file")));
	        break;
		  case "get_node":
		        break;
		   default:
		        break;
		}

		return result.toString();
	}


	@Override
	public void clear() throws IOException, CouldNotCreateDirectory {
		FileUtils.deleteDirectory(new File(base));
		createBaseDir();
	}

}
