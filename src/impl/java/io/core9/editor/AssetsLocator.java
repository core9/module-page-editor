package io.core9.editor;

import java.io.File;
import java.util.Map;

public class AssetsLocator {

	private String pathPrefix;
	private String healthFileName = "health.txt";
	private String blockDir = "blocks";
	private EditorRequest request;
	private String blockRepositoryDirectory;
	private String siteDir = "site";
	private String siteJson = "site.json";
	private String siteConfigFile;
	private String requestJsonDataUrl = "/site/data.json";
	private String healthFile;
	//private String gitModulePrefix = "data/git/";
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

	}

	public String getClientId() {
		return "9a8eccd84f9c40c791281139a87da7b645f25fab";// request.getClient();
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

	public String getTemplateFilePath() {
		return getPagePath() + templateFileName;
	}

	public String getOrgTemplateFilePath() {
		return getPagePath() + orgTemplateFileName;
	}

	public String getTemplateCacheFilePath() {
		return getPagePath() + templateCacheFileName;
	}

	public String getHostPath() {
		return pathPrefix + "/" + getClientId() + "/site/pages/" + request.getHost();
	}

	public String getDataBlockPath(String block, String type) {
		return getHostPath() + getPath() + getJsonDataFile(block, type);
	}

	private String getJsonDataFile(String block, String type) {
		return "/data/block-" + block + "-type-" + type + ".json";
	}

	private String getPagePath() {
		String path = request.getPath();
		String lastChar = path.substring(path.length() - 1);
		String fileSeperator = "";
		if (!lastChar.equals("/")) {
			fileSeperator = "/";
		}
		return siteDirectory + File.separator + pageDir + File.separator + request.getHost() + path + fileSeperator;
	}

	public String getPathPrefix() {
		return pathPrefix;
	}

	public String getBlockRepositoryDirectory(String httpsRepositoryUrl) {
		String fileName = httpsRepositoryUrl.substring(httpsRepositoryUrl.lastIndexOf('/') + 1, httpsRepositoryUrl.length());
		String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));
		blockRepositoryDirectory = getBlockDirectory() + File.separator + fileNameWithoutExtn;
		return blockRepositoryDirectory;
	}

	public String getPageDataRequest() {
		Map<String, String> params = request.getParams();
		String[] state = params.get("state").split("-");
		return getHostPath() + params.get("page") + getJsonDataFile(state[2], state[4]);
	}

	public String getPath() {
		return request.getPath();
	}

	public String getRequestJsonDataUrl() {
		return requestJsonDataUrl;
	}

	public String getSiteAssetsPath() {
		return "/site/assets/";
	}

	public String getPageAssetsPath() {
		return "/site/pages/assets/";
	}

	public String getSiteBlockPath() {
		return "/site/blocks/";
	}

	public String getSitePath() {
		return "/site/";
	}

	public String getStaticFilePath(String filename) {

		if (filename.startsWith(getSiteAssetsPath())) {
			return getClientDirectory() + getPageAssetsPath() + filename.substring(getSiteAssetsPath().length());
		} else if (filename.startsWith(getSiteBlockPath())) {
			return getClientDirectory() + "/" + filename.substring(getSitePath().length());
		} else if (filename.startsWith(getRequestJsonDataUrl())) {
			return getPageDataRequest();
		}
		return getClientDirectory() + getSiteAssetsPath();
	}


}
