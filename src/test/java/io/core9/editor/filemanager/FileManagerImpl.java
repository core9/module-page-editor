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
		if (!baseDir.exists())
			baseDir.mkdirs();
		if (!baseDir.exists())
			throw new CouldNotCreateDirectory(baseDir);
	}

	@Override
	public String[] lst(String id, String with_root) {
		return null;
	}

	@Override
	public JSONObject data(String id) throws IOException {
		JSONObject result = new JSONObject();

		if (id.indexOf(":") != -1) {
			// $id = array_map(array($this, 'id'), explode(":', $id));
			// return array('type'=>'multiple', 'content'=> 'Multiple selected:
			// ' . implode(' ', $id));
			result.put("type", "multiple");
			result.put("content", "Multiple selected: ..");
			return result;
		}

		String dir = getAbsolutePathFromId(id);
		if (new File(dir).isDirectory()) {
			result.put("type", "folder");
			result.put("content", id);
			return result;
		}

		if (new File(dir).isFile()) {
			String[] fileParts = dir.split(".");
			String ext = "";
			if (fileParts.length == 2) {
				ext = fileParts[1];
			}
			result.put("type", ext);
			result.put("content", "");

			switch (ext) {
			case "txt":
				result.put("content", "");// get content
				break;
			case "text":
			case "md":
			case "js":
			case "json":
			case "css":
			case "html":
			case "htm":
			case "xml":
			case "c":
			case "cpp":
			case "h":
			case "sql":
			case "log":
			case "py":
			case "rb":
			case "htaccess":
			case "php":
				result.put("content", "");// get content
				break;
			case "jpg":
			case "jpeg":
			case "gif":
			case "png":
			case "bmp":
				// $dat['content'] =
				// 'data:'.finfo_file(finfo_open(FILEINFO_MIME_TYPE),
				// $dir).';base64,'.base64_encode(file_get_contents($dir));
			default:
				result.put("content", "File not recognized: " + getIdFromPath(dir));
				break;

			}
		} else {
			throw new FileNotFoundException("Not a valid selection: " + getIdFromPath(dir));
		}
		return result;
	}

	@Override
	public JSONObject create(String id, String name, boolean mkdir) throws IOException {

		String dir = getAbsolutePathFromId(id);
		if (mkdir) {
			new File(dir + File.separator + name).mkdirs();
		} else {
			new File(dir + File.separator + name).createNewFile();
		}

		JSONObject result = new JSONObject();
		result.put("id", getIdFromPath(dir + File.separator + name));

		return result;

	}

	private String getIdFromPath(String path) throws IOException {
		path = real(path);
		path = path.substring(base.length());
		path = path.replace(File.separator, "/");
		if (path.length() == 0) {
			return "/";
		}
		return path;
	}

	private String getAbsolutePathFromId(String id) throws IOException {
		return real(base + File.separator + id.replace("/", File.separator).trim());
	}

	private String real(String path) throws IOException {
		String temp = new File(path).getCanonicalPath();
		if (base != null && base.length() != 0) {
			if (temp.indexOf(base) == -1)
				throw new FileNotFoundException("Path is not inside base (" + base + "): " + temp);
		}
		return temp;
	}

	@Override
	public void rename(String id, String name) {
	}

	@Override
	public JSONObject remove(String id) throws IOException {
		String dir = getAbsolutePathFromId(id);
		if (dir.equals(base))
			throw new FileNotFoundException("Cannot remove root");
		String status = "";
		if (new File(dir).isDirectory()) {
			FileUtils.deleteDirectory(new File(dir));
			status = "could not delete file";
		}
		if (new File(dir).isFile()) {
			FileUtils.deleteQuietly(new File(dir));
		}
		JSONObject result = new JSONObject();
		if (new File(dir).exists()) {
			result.put("status", status);
		} else {
			result.put("status", "OK");
		}
		return result;
	}

	@Override
	public void move(String id, String par) {
	}

	@Override
	public JSONObject copy(String id, String parent) throws IOException {

		String fileOrDirToBeCopied = getAbsolutePathFromId(id);
		parent = getAbsolutePathFromId(parent);

		String[] dirParts = fileOrDirToBeCopied.split(File.separator);

		String newFile = dirParts[dirParts.length - 1];
		newFile = parent + File.separator + newFile;

		if (new File(newFile).exists())
			throw new FileExistsException("Path already exists: " + newFile);

		if (new File(fileOrDirToBeCopied).isFile()) {
			FileUtils.copyFile(new File(fileOrDirToBeCopied), new File(newFile));
		}

		if (new File(fileOrDirToBeCopied).isDirectory()) {
			FileUtils.copyDirectory(new File(fileOrDirToBeCopied), new File(newFile));
		}

		JSONObject result = new JSONObject();
		result.put("id", getIdFromPath(newFile));

		return result;

	}

	@Override
	public String action(FileManagerRequest req) throws IOException {
		Object result = "";
		switch (req.getOperation()) {
		case "create_node":
			result = this.create(req.getId(), req.getName(), (!req.getType().equals("file")));
			break;
		case "delete_node":
			result = this.remove(req.getId());
			break;
		case "copy_node":
			result = this.copy(req.getId(), req.getParent());
			break;
		case "get_content":
			result = this.data(req.getId());
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
