package io.core9.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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
	private String siteJson = "site.json";
	private String siteConfigFile;
	private String dataPrefix = "/site/data.json";
	private String healthFile;
	private String gitModulePrefix = "data/git/";
	private String clientDirectory;
	private String siteDirectory;
	private String blockDirectory;
	private String pageDir = "pages";
	private String templateFileName = "template.html";
	private String orgTemplateFileName = "org.template.html";
	private String templateCacheFileName = "cache.html";

	public AssetsManagerImpl(String pathPrefix, EditorRequest request) {
		this.pathPrefix = pathPrefix;
		this.request = request;
		createPaths(request);
	}

	private void createPaths(EditorRequest req) {
		this.healthFile = pathPrefix + File.separator + healthFileName;
		this.clientDirectory = pathPrefix + File.separator + getClientId();
		this.siteDirectory = clientDirectory + File.separator + siteDir;
		this.blockDirectory = clientDirectory + File.separator + blockDir;
		this.siteConfigFile = siteDirectory + File.separator + siteJson;
		this.siteRepositoryDirectory = "../.." + File.separator + siteDirectory;
	}

	private String getTemplateFilePath() {
		return gitModulePrefix + getPagePath() + templateFileName;
	}

	private String getOrgTemplateFilePath() {
		return gitModulePrefix + getPagePath() + orgTemplateFileName;
	}

	private String getTemplateCacheFilePath() {
		return gitModulePrefix + getPagePath() + templateCacheFileName;
	}

	private String getHostPath() {
		return pathPrefix + "/" + getClientId() + "/site/pages/" + request.getHost();
	}

	private String getDataBlockPath(String block, String type) {
		return "/data/block-" + block + "-type-" + type + ".json";
	}

	private String getBlockRepositoryDirectory(String httpsRepositoryUrl) {
		String fileName = httpsRepositoryUrl.substring(httpsRepositoryUrl.lastIndexOf('/') + 1, httpsRepositoryUrl.length());
		String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
		blockRepositoryDirectory = "../.." + File.separator + blockDirectory + File.separator + fileNameWithoutExtn;
		return blockRepositoryDirectory;
	}

	private String getPagePath() {
		String path = request.getPath();
		String lastChar = path.substring(path.length() - 1);
		String fileSeperator = "";
		if (!lastChar.equals("/")) {
			fileSeperator = "/";
		}
		return getSiteRepositoryDirectory() + File.separator + pageDir + File.separator + request.getHost() + path + fileSeperator;
	}

	@Override
	public String getClientId() {
		return request.getClient();
	}

	@Override
	public void createWorkingDirectory() {
		FileUtils.createDirectory(pathPrefix);
		File yourFile = new File(healthFile);
		if (!yourFile.exists()) {
			try {
				yourFile.createNewFile();
			} catch (IOException e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
	}

	@Override
	public boolean checkWorkingDirectory() {
		return new File(healthFile).exists();
	}

	@Override
	public void deleteWorkingDirectory() {
		FileUtils.deleteDirectory(new File(pathPrefix));
	}

	@Override
	public void createClientDirectory() {
		FileUtils.createDirectory(clientDirectory);
	}

	@Override
	public boolean checkClientDirectory() {
		return new File(clientDirectory).exists();
	}

	@Override
	public void deleteClientDirectory() {
		FileUtils.deleteDirectory(new File(clientDirectory));
	}

	@Override
	public boolean checkSiteDirectory() {
		return new File(siteDirectory).exists();
	}

	@Override
	public void createSiteDirectory() {
		FileUtils.createDirectory(siteDirectory);
	}

	@Override
	public void deleteSiteDirectory() {
		FileUtils.deleteDirectory(new File(siteDirectory));
	}

	@Override
	public void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException {
		createBlockDirectory();
		if (checkBlockDirectoryIfExists()) {
			GitHandlerImpl.clonePublicGitRepository(httpsRepositoryUrl, getBlockRepositoryDirectory(httpsRepositoryUrl));
		} else {
			throw new FileNotFoundException(blockDirectory);
		}

	}

	@Override
	public String getBlockDirectory() {
		return blockDirectory;
	}

	@Override
	public boolean checkIfRepositoryDirectoryExists() {
		return new File(gitModulePrefix + blockRepositoryDirectory).exists();
	}

	@Override
	public JSONObject getSiteConfig() {
		JSONObject obj = new JSONObject();
		try {
			obj = (JSONObject) JSONValue.parseStrict(FileUtils.readFile(getSiteConfigFile(), StandardCharsets.UTF_8));
		} catch (ParseException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		return obj;
	}

	@Override
	public boolean checkSite() {
		return new File(getSiteConfigFile()).exists();
	}

	@Override
	public void clonePublicSiteFromGit(String httpsRepositoryUrl) {
		GitHandlerImpl.clonePublicGitRepository(httpsRepositoryUrl, getSiteRepositoryDirectory());
	}

	@Override
	public String getSiteConfigFile() {
		return siteConfigFile;
	}

	@Override
	public String getSiteRepositoryDirectory() {
		return siteRepositoryDirectory;
	}

	@Override
	public boolean checkIfPageTemplateExists() {
		return new File(getTemplateFilePath()).exists();
	}

	@Override
	public String getPageTemplate() {
		return getTemplateFilePath();
	}

	@Override
	public void writePageCache(String content) {
		File newfile = new File(getOrgTemplateFilePath());
		if (!newfile.exists()) {
			if (!new File(getTemplateFilePath()).renameTo(newfile))
				System.out.println("Rename failed");
		}
		FileUtils.writeToFile(getTemplateFilePath(), content);
		FileUtils.writeToFile(getTemplateCacheFilePath(), content);
	}

	@Override
	public String getCachedPage() {
		return FileUtils.readFile(getTemplateCacheFilePath(), StandardCharsets.UTF_8);
	}

	@Override
	public String getStaticFilePath(String filename) {
		if (filename.startsWith("/site/assets/")) {
			return clientDirectory + "/site/pages/assets/" + filename.substring("/site/assets/".length());
		} else if (filename.startsWith("/site/blocks/")) {
			return clientDirectory + "/" + filename.substring("/site/".length());
		} else if (filename.startsWith(dataPrefix)) {
			return getPageDataRequest();
		}
		return pathPrefix + "/" + getClientId() + "/site/assets/";
	}

	@Override
	public void saveBlockData(JSONObject meta, JSONObject editorData) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("meta", meta);
		jsonObject.put("data", editorData);
		FileUtils.writeToFile(getHostPath() + request.getPath() + getDataBlockPath(meta.getAsString("block"), meta.getAsString("type")), jsonObject.toJSONString());
	}

	@Override
	public String getPageDataRequest() {
		Map<String, String> params = request.getParams();
		String[] state = params.get("state").split("-");
		return getHostPath() + params.get("page") + getDataBlockPath(state[2], state[4]);
	}

	@Override
	public void getPageData() {

	}

	private void createBlockDirectory() {
		FileUtils.createDirectory(getBlockDirectory());
	}

	private boolean checkBlockDirectoryIfExists() {
		return new File(blockDirectory).exists();
	}




}
