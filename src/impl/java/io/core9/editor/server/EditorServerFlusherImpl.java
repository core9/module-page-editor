package io.core9.editor.server;

import io.core9.editor.client.ClientRepositoryImpl;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;
import net.minidev.json.JSONObject;
import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorServerFlusherImpl implements EditorServerFlusher {

	@InjectPlugin
	private Server server;

	@Override
	public void execute() {
		
		server.use("/plugins/editor/backup(.*)", new Middleware() {
			@Override
			public void handle(Request req) {




			}
		});
		

		server.use("/plugins/editor/flush(.*)", new Middleware() {
			@Override
			public void handle(Request req) {


				if(!req.getParams().isEmpty()){
					flushClientSiteTemplates(req);
				}else{
					flushClientSiteEnvironment(req);
				}


			}

			private void flushClientSiteTemplates(Request req) {
				String host = req.getVirtualHost().getHostname();
				req.getResponse().end("also possible /plugins/editor/flush?page=/scraper/nl&flush=template : " + host);
				BlockTool blockTool = new LoadClientSiteTemplatesAndKeepData();
				JSONObject data = new JSONObject();
				data.put("host", ClientRepositoryImpl.cleanHost(host));
				data.put("url", "http://" + host + req.getParams().get("page"));
				data.put("flush", req.getParams().get("flush"));
				data.put("page", req.getParams().get("page"));
				blockTool.setData("data/editor", data);
			}

			private void flushClientSiteEnvironment(Request req) {
				String host = req.getVirtualHost().getHostname();
				req.getResponse().end("also possible /plugins/editor/flush?page=/scraper/nl&flush=template : " + host);
				BlockTool blockTool = new RecreateClientSiteEnvironment();
				JSONObject data = new JSONObject();
				data.put("host", ClientRepositoryImpl.cleanHost(host));
				data.put("url", "http://" + host + req.getPath());
				blockTool.setData("data/editor", data);
			}


		});

	}

}
