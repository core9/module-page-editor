package io.core9.editor.client;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorClientDataHandler;
import io.core9.editor.EditorClientPlugin;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.abtest.ABEngine;
import io.core9.editor.data.ClientData;
import io.core9.module.auth.AuthenticationPlugin;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.template.closure.ClosureTemplateEngine;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@PluginImplementation
public class EditorClientDataHandlerImpl implements EditorClientDataHandler<EditorClientDataHandlerConfig> {

	private static Logger logger = Logger.getLogger(EditorClientDataHandlerImpl.class.getName());

	private static final String pathPrefix = "data/editor";


	@InjectPlugin
	private EditorClientPlugin editorClientPlugin;

	@InjectPlugin
	private ClosureTemplateEngine engine;

	@InjectPlugin
	private AuthenticationPlugin auth;


	private AssetsManager assetsManager;
	private EditorRequestImpl request;

	@Override
	public String getName() {
		return "Editor client";
	}

	@Override
	public Class<? extends DataHandlerFactoryConfig> getConfigClass() {
		return EditorClientDataHandlerConfig.class;
	}

	@Override
	public DataHandler<EditorClientDataHandlerConfig> createDataHandler(DataHandlerFactoryConfig options) {

		final EditorClientDataHandlerConfig config = (EditorClientDataHandlerConfig) options;

		return new DataHandler<EditorClientDataHandlerConfig>() {

			private Document doc;


			@Override
			public Map<String, Object> handle(Request req) {
				Map<String, Object> result = new HashMap<String, Object>();

				Map<String, Object> test = ABEngine.getTestVars(auth, req);
				System.out.println("user has test : " + test);



				@SuppressWarnings("unused")
				String path = req.getPath();
				@SuppressWarnings("unused")
				String host = req.getHostname();
				//FIXME weird piece of code
/*				if(!path.startsWith("/p/scraper/nl") && host.indexOf("easydrain") != -1){
					result.put("head", "");
					result.put("body", "");
					return result;
				}*/


				request = new EditorRequestImpl();
				request.setClientRepository(ClientData.getRepository());
				String absoluteUrl = "http://" + req.getHostname() + req.getPath();
				request.setAbsoluteUrl(absoluteUrl);

				assetsManager = new AssetsManagerImpl(pathPrefix, request);
				//assetsManager.deleteWorkingDirectory();
				if (!assetsManager.checkWorkingDirectory()) {
					assetsManager.createWorkingDirectory();
				}

				Document document;
				try {
					 document = Jsoup.parse(assetsManager.getCachedPage());
				} catch (Exception e) {
					document = Jsoup.parse(readFile(assetsManager.getPageTemplatePath(), StandardCharsets.UTF_8));
				}

				result.put("head", document.head().toString());
				result.put("body", document.body().toString());

				return result;
			}



			private String readFile(String path, Charset encoding) {
				byte[] encoded = null;
				try {
					encoded = Files.readAllBytes(Paths.get(path));
				} catch (IOException e) {
					logger.log(Level.INFO, e.getMessage());
				}
				String result = "";
				if(encoded == null){
					logger.log(Level.INFO, "File does not exists");
				}else{
					result = new String(encoded, encoding);
				}
				return result;
			}

			@SuppressWarnings("unused")
			private Map<String, Object> getBackupUrl(Map<String, Object> result, String url) {
				String urlFileName = url.replace("/", "_");

				createFileInDataDir(urlFileName);

				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e) {
					logger.log(Level.INFO, e.getMessage());
				}

				result.put("head", doc.head().toString());
				result.put("body", doc.body().toString());
				return result;
			}

			private void createFileInDataDir(String fileName) {
				new File(pathPrefix).mkdirs();
				File yourFile = new File(pathPrefix + "/" + fileName);
				if (!yourFile.exists()) {
					try {
						yourFile.createNewFile();
					} catch (IOException e) {
						logger.log(Level.INFO, e.getMessage());
					}
				}
			}

			@Override
			public EditorClientDataHandlerConfig getOptions() {
				return config;
			}

		};
	}
	/*
	 * switch(req.getMethod()) { case POST: sendMail(config, req);
	 * result.put("sent", true); default: if(config.getCustomVariables() !=
	 * null) { for(CustomVariable var : config.getCustomVariables()) {
	 * if(var.isManual()) { req.getResponse().addGlobal(var.getKey(),
	 * var.getValue()); } else { req.getResponse().addGlobal(var.getKey(),
	 * req.getParams().get(var.getValue())); } } } }
	 */

}
