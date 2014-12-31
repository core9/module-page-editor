package io.core9.editor;

import java.io.FileNotFoundException;

import net.minidev.json.JSONObject;

public interface AssetsManager {

	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	String getClientId();

	void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException;

	void createClientDirectory();

	boolean checkClientDirectory();

	void deleteClientDirectory();

	boolean checkSiteDirectory();

	void createSiteDirectory();

	void deleteSiteDirectory();

	String getBlockDirectory();

	boolean checkIfRepositoryDirectoryExists();

	boolean checkSite();

	JSONObject getSiteConfig();

	void clonePublicSiteFromGit(String httpsRepositoryUrl);

	String getSiteConfigFile();

	String getPageTemplatePath();

	boolean checkIfPageTemplateExists();

	void writePageCache(String string);

	String getCachedPage();

	String getStaticFilePath(String filename);

	void saveBlockData(JSONObject meta, JSONObject editorData);

	String getPageDataRequest();

	void getPageData();

	String getSiteDirectory();

	String getPageOriginalTemplate();

	String getPageCachedTemplate();

	void commitPublicSiteToGit(String httpsRepositoryUrl, String user,
			String password);

}
