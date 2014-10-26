package io.core9.editor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.ParseException;

public class AssetsManagerImpl implements AssetsManager {

	private static Logger logger = Logger.getLogger(AssetsManagerImpl.class.getName());

	private AssetsLocator assets;
	private String httpsRepositoryUrl;

	public AssetsManagerImpl(String pathPrefix, EditorRequest request) {
		assets = new AssetsLocator(pathPrefix, request);
	}

	@Override
	public String getClientId() {
		return assets.getClientId();
	}

	@Override
	public void createWorkingDirectory() {
		FileUtils.createDirectory(assets.getPathPrefix());
		File yourFile = new File(assets.getHealthFile());
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
		return new File(assets.getHealthFile()).exists();
	}

	@Override
	public void deleteWorkingDirectory() {
		FileUtils.deleteDirectory(new File(assets.getPathPrefix()));
	}

	@Override
	public void createClientDirectory() {
		FileUtils.createDirectory(assets.getClientDirectory());
	}

	@Override
	public boolean checkClientDirectory() {
		return new File(assets.getClientDirectory()).exists();
	}

	@Override
	public void deleteClientDirectory() {
		FileUtils.deleteDirectory(new File(assets.getClientDirectory()));
	}

	@Override
	public boolean checkSiteDirectory() {
		return new File(assets.getSiteDirectory()).exists();
	}

	@Override
	public void createSiteDirectory() {
		FileUtils.createDirectory(assets.getSiteDirectory());
	}

	@Override
	public void deleteSiteDirectory() {
		FileUtils.deleteDirectory(new File(assets.getSiteDirectory()));
	}

	@Override
	public void cloneBlocksFromGit(String httpsRepositoryUrl) throws FileNotFoundException {
		this.httpsRepositoryUrl = httpsRepositoryUrl;
		FileUtils.createDirectory(getBlockDirectory());
		if (new File(assets.getBlockDirectory()).exists()) {
			GitHandlerImpl.clonePublicGitRepository(httpsRepositoryUrl, assets.getBlockRepositoryDirectory(httpsRepositoryUrl));
		} else {
			throw new FileNotFoundException(assets.getBlockDirectory());
		}

	}

	@Override
	public String getBlockDirectory() {
		return assets.getBlockDirectory();
	}

	@Override
	public boolean checkIfRepositoryDirectoryExists() {
		return new File(assets.getBlockRepositoryDirectory(httpsRepositoryUrl)).exists();
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
		GitHandlerImpl.clonePublicGitRepository(httpsRepositoryUrl, getSiteDirectory());
	}

	@Override
	public String getSiteConfigFile() {
		return assets.getSiteConfigFile();
	}

	@Override
	public String getSiteDirectory() {
		return assets.getSiteDirectory();
	}

	@Override
	public boolean checkIfPageTemplateExists() {
		return new File(assets.getTemplateFilePath()).exists();
	}

	@Override
	public String getPageTemplate() {
		return assets.getTemplateFilePath();
	}

	@Override
	public String getPageOriginalTemplate() {
		return assets.getOrgTemplateFilePath();
	}

	@Override
	public String getPageCachedTemplate() {
		return assets.getTemplateCacheFilePath();
	}

	@Override
	public void writePageCache(String content) {
		File newfile = new File(assets.getOrgTemplateFilePath());
		if (!newfile.exists()) {
			if (!new File(assets.getTemplateFilePath()).renameTo(newfile))
				System.out.println("Rename failed");
		}
		FileUtils.writeToFile(assets.getTemplateFilePath(), content);
		FileUtils.writeToFile(assets.getTemplateCacheFilePath(), content);
	}

	@Override
	public String getCachedPage() {
		return FileUtils.readFile(assets.getTemplateCacheFilePath(), StandardCharsets.UTF_8);
	}

	@Override
	public String getStaticFilePath(String filename) {
		return assets.getStaticFilePath(filename);
	}

	@Override
	public void saveBlockData(JSONObject meta, JSONObject editorData) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("meta", meta);
		jsonObject.put("data", editorData);
		FileUtils.writeToFile(assets.getDataBlockPath(meta.getAsString("block"), meta.getAsString("type")), jsonObject.toJSONString());
	}

	@Override
	public String getPageDataRequest() {
		return assets.getPageDataRequest();
	}

	@Override
	public void getPageData() {

	}





}
