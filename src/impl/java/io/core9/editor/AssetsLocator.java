package io.core9.editor;

import java.io.File;
import java.util.Map;

public class AssetsLocator {

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

	public AssetsLocator(String pathPrefix, EditorRequest request) {
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

	public String getClientId() {
		return request.getClient();
	}

	public String getHealthFile() {
		return healthFile;
	}

	public String getClientDirectory() {
		return clientDirectory;
	}

	public String getSiteDirectory() {
		return siteDirectory;
	}

	public String getBlockDirectory() {
		return blockDirectory;
	}

	public String getSiteConfigFile() {
		return siteConfigFile;
	}

	public String getSiteRepositoryDirectory() {
		return siteRepositoryDirectory;
	}

	public String getTemplateFilePath() {
		return gitModulePrefix + getPagePath() + templateFileName;
	}

	public String getOrgTemplateFilePath() {
		return gitModulePrefix + getPagePath() + orgTemplateFileName;
	}

	public String getTemplateCacheFilePath() {
		return gitModulePrefix + getPagePath() + templateCacheFileName;
	}

	public String getHostPath() {
		return pathPrefix + "/" + getClientId() + "/site/pages/" + request.getHost();
	}

	public String getDataBlockPath(String block, String type) {
		return "/data/block-" + block + "-type-" + type + ".json";
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

	public String getPathPrefix() {
		return pathPrefix;
	}

	public String getBlockRepositoryDirectory(String httpsRepositoryUrl) {
		String fileName = httpsRepositoryUrl.substring(httpsRepositoryUrl.lastIndexOf('/') + 1, httpsRepositoryUrl.length());
		String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
		blockRepositoryDirectory = "../.." + File.separator + getBlockDirectory() + File.separator + fileNameWithoutExtn;
		return blockRepositoryDirectory;
	}

	public String getPageDataRequest() {
		Map<String, String> params = request.getParams();
		String[] state = params.get("state").split("-");
		return getHostPath() + params.get("page") + getDataBlockPath(state[2], state[4]);
	}

	public String getPath() {
		return request.getPath();
	}
}
