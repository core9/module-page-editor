package io.core9.editor.admin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;

import org.apache.commons.io.FileExistsException;
import org.apache.commons.io.FileUtils;


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
	public JSONArray lst(String id, boolean withRoot) throws IOException {
		String dir = getAbsolutePathFromId(id);
		File[] lst = new File(dir).listFiles();
		JSONArray fileList = new JSONArray();
		for (File fileObj : lst) {
			String fileName = getIdFromPath(fileObj.getCanonicalPath());
			if (FileManagerRequest.isValidFilename(fileName)) {

				if (fileObj.isDirectory()) {
					JSONObject directory = new JSONObject();
					directory.put("text", fileObj.getName());
					directory.put("children", true);
					directory.put("id", fileName);
					directory.put("icon", "folder folder-" + getDirectoryExtention(fileName));
					fileList.add(directory);
				} else {
					JSONObject file = new JSONObject();
					file.put("text", fileObj.getName());
					file.put("children", false);
					file.put("id", fileName);
					file.put("type", "file");
					file.put("icon", "file file-" + getFileExtention(fileName));
					fileList.add(file);
				}

			}

		}

		if (withRoot && getIdFromPath(dir).equals("/")) {
			JSONArray rootArray = new JSONArray();
			JSONObject parent = new JSONObject();
			parent.put("text", new File(base).getName());
			parent.put("children", fileList);
			parent.put("id", "/");
			parent.put("icon", "folder");
			JSONObject state = new JSONObject();
			state.put("opened", true);
			state.put("disabled", true);
			parent.put("state", state);

			rootArray.add(parent);
			return rootArray;

		}

		return fileList;
	}

	private String getDirectoryExtention(String fileName) {
		System.out.println(fileName);
		return "";
	}

	private String getFileExtention(String fileName) {
		String[] fileParts = fileName.split(".");
		if (fileParts.length == 2) {
			return fileParts[1];
		}
		return "noext";

	}
	
	private static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}

	@Override
	public JSONObject data(String id) throws IOException {
		JSONObject result = new JSONObject();

		if (id.indexOf(":") != -1) {
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
			String[] fileParts = dir.split("\\.");
			String ext = "";
			if (fileParts.length >= 2) {
				ext = fileParts[fileParts.length-1];
			}
			result.put("type", ext);
			result.put("content", "");

			switch (ext) {
			case "txt":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "text":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "md":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "js":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "json":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "css":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "html":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "htm":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "xml":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "soy":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
			case "c":
			case "cpp":
			case "h":
			case "sql":
			case "log":
				result.put("content", readFile(dir, StandardCharsets.UTF_8));
				break;
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
	public JSONObject rename(String id, String name) throws IOException {
		String fileToBeRenamed = getAbsolutePathFromId(id);
		if (fileToBeRenamed.equals(base)) {
			throw new FileAlreadyExistsException("Cannot rename root");
		}
		if (!FileManagerRequest.isValidName(name)) {
			throw new FileAlreadyExistsException("Invalid name " + name);
		}

		File orgFile = new File(fileToBeRenamed);
		Path newFile = orgFile.toPath().resolveSibling(name);
		if (newFile.toFile().exists()) {
			throw new FileAlreadyExistsException("File already exists " + name);
		}
		Files.move(orgFile.toPath(), newFile);

		JSONObject result = new JSONObject();
		result.put("id", getIdFromPath(newFile.toFile().getCanonicalPath()));
		return result;
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
	public JSONObject move(String id, String par) throws IOException {
		String dir = getAbsolutePathFromId(id);
		String parent = getAbsolutePathFromId(par);
		File orgFile = new File(dir);
		File newFile = new File(parent);
		if (orgFile.isDirectory()) {
			File movedDir = new File(newFile.getCanonicalFile() + File.separator + orgFile.getName());
			FileUtils.moveDirectory(orgFile, movedDir);
		}
		if (orgFile.isFile()) {
			File movedFile = new File(newFile.getCanonicalFile() + File.separator + orgFile.getName());
			FileUtils.moveFile(orgFile, movedFile);
		}

		JSONObject result = new JSONObject();
		result.put("id", getIdFromPath(newFile.getCanonicalPath()));
		return result;
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
	public String save(String id, String content) throws IOException {
		String file = getAbsolutePathFromId(id);
		PrintWriter writer = new PrintWriter(file, "UTF-8");
		writer.println(content);
		writer.close();
		return content;
	}

	@Override
	public String action(FileManagerRequest req) throws IOException {
		// ("get_node", "get_content", "create_node", "rename_node",
		// "delete_node", "move_node", "copy_node"));
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
			break;
		case "get_node":
			result = this.lst(req.getId(), req.getId().equals("/"));
			break;
		case "rename_node":
			result = this.rename(req.getId(), req.getName());
			break;
		case "move_node":
			result = this.move(req.getId(), req.getParent());
			break;
		case "save_content":
			result = this.save(req.getId(), req.getContent());
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
