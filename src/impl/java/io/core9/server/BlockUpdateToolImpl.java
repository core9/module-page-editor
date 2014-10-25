package io.core9.server;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.Block;
import io.core9.editor.ClientRepository;
import io.core9.editor.JsonSoyUtils;
import io.core9.editor.PageParser;
import io.core9.editor.PageParserImpl;
import io.core9.editor.RequestImpl;
import io.core9.editor.data.ClientData;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import net.minidev.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import com.google.template.soy.SoyFileSet;
import com.google.template.soy.data.SoyMapData;
import com.google.template.soy.tofu.SoyTofu;

public class BlockUpdateToolImpl implements BlockTool {

	private String blockClassName = ".block";
	private String blockContainer = "#main-section";
	private PageParser parser;
	private JSONObject data;
	private String pathPrefix;
	private AssetsManager assetsManager;
	private RequestImpl request;
	//private String httpsSiteRepositoryUrl = "https://github.com/jessec/site-kennispark.git";
	//private String httpsBlockRepositoryUrl = "https://github.com/jessec/block-video.git";


	@Override
	public void setData(String prefix, JSONObject data) {
		this.pathPrefix = prefix;
		this.data = data;
		processData();
	}

	private void processData() {

		request = new RequestImpl();
		request.setClientRepository(ClientData.getRepository());

		JSONObject meta = (JSONObject) data.get("meta");

		JSONObject editorData = (JSONObject) data.get("editor");

		String absoluteUrl = meta.getAsString("absolute-url");

		request.setAbsoluteUrl(absoluteUrl);


		assetsManager = new AssetsManagerImpl(pathPrefix, request);
		// assetsManager.deleteWorkingDirectory();
		if (!assetsManager.checkWorkingDirectory()) {
			assetsManager.createWorkingDirectory();
		}




		File siteConfig = new File(assetsManager.getSiteConfigFile());
		if (!siteConfig.exists()) {

			//FIXME same code in block command
			assetsManager.deleteClientDirectory();
			assetsManager.createClientDirectory();
			String clientId = assetsManager.getClientId();

			ClientRepository repository = ClientData.getRepository();
			String siteRepoUrl = repository.getSiteRepository(clientId);

			assetsManager.clonePublicSiteFromGit(siteRepoUrl);
			JSONObject config = assetsManager.getSiteConfig();
			System.out.println(config);


			try {

				List<String> blockRepos = repository.getBlockRepository(clientId);
				for(String repo : blockRepos){
					assetsManager.cloneBlocksFromGit(repo);
				}


			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

		} else {

		}

		System.out.println(assetsManager.getPageTemplate());

		if (assetsManager.checkIfPageTemplateExists()) {
			String pageTemplate = assetsManager.getPageTemplate();
			System.out.println("");

			File testPage = new File(pageTemplate);
			if (testPage.exists()) {
				parser = new PageParserImpl(testPage, blockContainer, blockClassName);


				Block block = new BlockImpl();

				if(meta.getAsString("state").equals("delete")){
					parser.deleteBlock(Integer.parseInt((String) meta.get("block")));
				}

				if(meta.getAsString("state").equals("insertbefore")){
					String blockTemplate = meta.getAsString("template");
					Element elem = parseSoyTemplateToElement(assetsManager.getClientId(), blockTemplate , editorData);
					block.addElement(elem);
					int pos = 0;
					int selectedPos = Integer.parseInt((String) meta.get("block"));
					if(selectedPos > 1){
						pos = selectedPos - 1;
					}
					parser.insertBlock(pos, block);
				}

				if(meta.getAsString("state").equals("insertafter")){
					String blockTemplate = meta.getAsString("template");
					Element elem = parseSoyTemplateToElement(assetsManager.getClientId(), blockTemplate , editorData);
					block.addElement(elem);
					int selectedPos = Integer.parseInt((String) meta.get("block"));
					int pos = selectedPos + 1;
					parser.insertBlock(pos, block);
				}

				if(meta.getAsString("state").equals("edit")){
					String blockTemplate = meta.getAsString("template");
					blockTemplate = blockTemplate.substring(11);

					Element elem = parseSoyTemplateToElement(assetsManager.getClientId() + "/", blockTemplate , editorData);
					block.addElement(elem);
					parser.replaceBlock(Integer.parseInt((String) meta.get("block")), block );
				}





				String htmlTemplate = parser.getPage();

				if(!htmlTemplate.isEmpty()){
					assetsManager.saveBlockData(meta, editorData);
				}

				System.out.println(htmlTemplate);
				Document document = Jsoup.parse(htmlTemplate);
				assetsManager.writePageCache(document.toString());
			}
		}
	}

	private Element parseSoyTemplateToElement(String clientId, String blockTemplate, JSONObject data) {
		 // Bundle the Soy files for your project into a SoyFileSet.

		String[] tpl = blockTemplate.split("/");

		String name = tpl[tpl.length - 1];
		String[] nm = name.split("\\.");
		String x = nm[0];

		String soyNameSpace = x.replace("-", ".");

		String blockDirectory = assetsManager.getBlockDirectory();

		String soyTemplate = blockDirectory + blockTemplate;

	    SoyFileSet sfs = new SoyFileSet.Builder().add(new File(soyTemplate)).build();

	    // Compile the template into a SoyTofu object.
	    // SoyTofu's newRenderer method returns an object that can render any template in the file set.
	    SoyTofu tofu = sfs.compileToTofu();


	    SoyMapData soyData = (SoyMapData) JsonSoyUtils.JsonToSoy(data.toJSONString());

	    String blockHtml = tofu.newRenderer(soyNameSpace).setData(soyData).render();



	    System.out.println(blockHtml);


	    Element tag = Jsoup.parse(blockHtml, "", Parser.xmlParser());

		return tag;
	}

	@Override
	public String getResponse() {
		return null;
	}

}
