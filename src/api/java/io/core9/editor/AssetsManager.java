package io.core9.editor;

import java.io.FileNotFoundException;

import net.minidev.json.JSONObject;

public interface AssetsManager {


	boolean checkWorkingDirectory();

	void createWorkingDirectory();

	void deleteWorkingDirectory();

	void setRequest(EditorRequest request);


	String getClientId();

	void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException;

	void createClientDirectory();

	boolean checkClientDirectory();

	void deleteClientDirectory();

	boolean checkSiteDirectory();

	void createSiteDirectory();

	void deleteSiteDirectory();

	String getBlockRepositoryDirectory();

	boolean checkIfRepositoryDirectoryExists();

	boolean checkSite();

	JSONObject getSiteConfig();

	void clonePublicSiteFromGit(String httpsRepositoryUrl);

	String getSiteRepositoryDirectory();

	String getSiteConfigFile();

	String getPageTemplate();

	boolean checkIfPageTemplateExists();

	void writePageCache(String string);

	String getCachedPage();

	String getStaticFilePath(String filename);

	void saveBlockData(JSONObject meta, JSONObject editorData);

	String getPageDataRequest();


}
