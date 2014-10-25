package io.core9.editor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

public class AssetsManagerImpl implements AssetsManager {

	private static Logger logger = Logger.getLogger(AssetsManagerImpl.class.getName());

	private String pathPrefix;
	private String healthFileName = "health.txt";
	private String blockDir = "blocks";
	private EditorRequest request;
	private String blockRepositoryDirectory;
	private String siteRepositoryDirectory;
	private String siteDir = "site";
	private String siteConfigFile;
	private String dataPrefix = "/site/data.json";

	@SuppressWarnings("unused")
	private String healthFile;



	public AssetsManagerImpl(String pathPrefix) {
		this.pathPrefix = pathPrefix;
	}

	@Override
	public void setRequest(EditorRequest request) {
		this.request = request;
		createPaths(request);
	}

	private void createPaths(EditorRequest req) {
		this.healthFile = pathPrefix + File.separator + healthFileName;
	}

	@Override
	public String getClientId() {
		return request.getClient();
	}

	@Override
	public void createWorkingDirectory() {
		createDirectory(pathPrefix);
		File yourFile = new File(healthFile);
		if (!yourFile.exists()) {
			try {
				yourFile.createNewFile();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
	}

	private void createDirectory(String directory) {
		if(!new File(directory).exists()){
			new File(directory).mkdirs();
		}
	}

	@Override
	public boolean checkWorkingDirectory() {
		return new File(healthFile).exists();
	}

	@Override
	public void deleteWorkingDirectory() {
		deleteDirectory(new File(pathPrefix));
	}

	private static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}



	@Override
	public void createClientDirectory() {
		createDirectory(pathPrefix + File.separator + getClientId());
	}

	@Override
	public boolean checkClientDirectory() {
		return new File(pathPrefix + File.separator + getClientId()).exists();
	}

	@Override
	public void deleteClientDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getClientId()));
	}

	@Override
	public boolean checkSiteDirectory() {
		return new File(pathPrefix + File.separator + getClientId() + File.separator + "site").exists();
	}

	@Override
	public void createSiteDirectory() {
		createDirectory(pathPrefix + File.separator + getClientId() + File.separator + "site");
	}

	@Override
	public void deleteSiteDirectory() {
		deleteDirectory(new File(pathPrefix + File.separator + getClientId() + File.separator + "site"));
	}

	@Override
	public void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException {
		createBlockDirectory();
		if (checkBlockDirectoryIfExists()) {
			String fileName = httpsRepositoryUrl.substring(httpsRepositoryUrl.lastIndexOf('/') + 1, httpsRepositoryUrl.length());
			String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
			blockRepositoryDirectory = "../.." + File.separator + pathPrefix + File.separator + getClientId() + File.separator + blockDir + File.separator + fileNameWithoutExtn;
			clonePublicGitRepository(httpsRepositoryUrl, blockRepositoryDirectory);
		} else {
			throw new FileNotFoundException(pathPrefix + File.separator + getClientId() + File.separator + blockDir);
		}

	}

	private void clonePublicGitRepository(String httpsRepositoryUrl, String repositoryDirectory) {
		GitHandler git = new GitHandlerImpl(httpsRepositoryUrl, repositoryDirectory);
		git.init();
	}

	private void createBlockDirectory() {
		createDirectory(getBlockRepositoryDirectory());
	}

	private boolean checkBlockDirectoryIfExists() {
		return new File(pathPrefix + File.separator + getClientId() + File.separator + blockDir).exists();
	}

	@Override
	public String getBlockRepositoryDirectory() {
		return blockRepositoryDirectory = pathPrefix + File.separator + getClientId() + File.separator + blockDir;
	}

	@Override
	public boolean checkIfRepositoryDirectoryExists() {
		return new File("data/git/" + blockRepositoryDirectory).exists();
	}

	@Override
	public JSONObject getSiteConfig() {
		String config = readFile(getSiteConfigFile(), StandardCharsets.UTF_8);
		JSONObject obj = new JSONObject();
		try {
			obj = (JSONObject) JSONValue.parseStrict(config);
		} catch (ParseException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		return obj;
	}

	@Override
	public boolean checkSite() {
		return new File(getSiteConfigFile()).exists();
	}


	private void writeToFile(String fileName, String content) {

		File targetFile = new File(fileName);
		File parent = targetFile.getParentFile();
		if(!parent.exists() && !parent.mkdirs()){
		    throw new IllegalStateException("Couldn't create dir: " + parent);
		}

		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
			writer.write(content);
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
	}

	private String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		return new String(encoded, encoding);
	}

	@Override
	public void clonePublicSiteFromGit(String httpsRepositoryUrl) {
		clonePublicGitRepository(httpsRepositoryUrl, getSiteRepositoryDirectory());
	}

	@Override
	public String getSiteConfigFile() {
		siteConfigFile = "data/git" + File.separator + getSiteRepositoryDirectory() + File.separator + "site.json";
		return siteConfigFile;
	}

	@Override
	public String getSiteRepositoryDirectory() {
		siteRepositoryDirectory = "../.." + File.separator + pathPrefix + File.separator + getClientId() + File.separator + siteDir;
		return siteRepositoryDirectory;
	}

	private String getPagePath(){
		String path = request.getPath();
		String lastChar = path.substring(path.length() - 1);
		String fileSeperator = "";
		if(!lastChar.equals("/")){
			fileSeperator = "/";
		}

		return getSiteRepositoryDirectory() + File.separator + "pages" + File.separator + request.getHost() + path + fileSeperator ;
	}

	@Override
	public boolean checkIfPageTemplateExists() {
		String path = "data/git/" +getPagePath() + "template.html";
		return new File(path).exists();
	}

	@Override
	public String getPageTemplate() {
		return "data/git/" +getPagePath() + "template.html";
	}

	@Override
	public void writePageCache(String content) {
		String templateFile = "data/git/" +getPagePath() + "template.html";
		String orgTemplateFile = "data/git/" +getPagePath() + "org.template.html";
		String cacheFile = "data/git/" +getPagePath() + "cache.html";

		File oldfile =new File(templateFile);
		File newfile =new File(orgTemplateFile);

		if(!newfile.exists()){
			if(oldfile.renameTo(newfile)){
				System.out.println("Rename succesful");
			}else{
				System.out.println("Rename failed");
			}
		}


		writeToFile(templateFile, content);
		writeToFile(cacheFile, content);
	}

	@Override
	public String getCachedPage(){
		String cacheFile = "data/git/" +getPagePath() + "cache.html";
		return  readFile(cacheFile, StandardCharsets.UTF_8);
	}

	@Override
	public String getStaticFilePath(String filename) {
		if(filename.startsWith("/site/assets/")){
			return pathPrefix + "/" + getClientId() + "/site/pages/assets/" + filename.substring("/site/assets/".length()) ;
		}else if(filename.startsWith("/site/blocks/")){
			return pathPrefix + "/" + getClientId() + "/" + filename.substring("/site/".length()) ;
		}else if(filename.startsWith(dataPrefix)){
			String path = getPageDataRequest();
			return path;
		}
		return pathPrefix + "/" + getClientId() + "/site/assets/";
	}

	@Override
	public void saveBlockData(JSONObject meta, JSONObject editorData) {
		//String pageDataFile = "data/git/" +getPagePath() + "data/block-" + meta.getAsString("block") + "-type-" + meta.getAsString("type") + ".json";

/*		Map<String, String> params = request.getParams();
		String[] state = params.get("state").split("-");*/

		String page = request.getPath();

		String block = meta.getAsString("block");

		String type = meta.getAsString("type");

		String pageDataPath = pathPrefix + "/" + getClientId() + "/site/pages/" + request.getHost() + page + "/data/block-"+block+"-type-"+type+".json";


		JSONObject jsonObject = new JSONObject();
		jsonObject.put("meta", meta);
		jsonObject.put("data", editorData);
		writeToFile(pageDataPath, jsonObject.toJSONString());
	}



	@Override
	public String getPageDataRequest(){


		Map<String, String> params = request.getParams();
		String[] state = params.get("state").split("-");

		String pageDataPath = pathPrefix + "/" + getClientId() + "/site/pages/" + request.getHost() + params.get("page") + "/data/block-"+state[2]+"-type-"+state[4]+".json";

		return pageDataPath;
	}

	@Override
	public void getPageData() {

	}

}
