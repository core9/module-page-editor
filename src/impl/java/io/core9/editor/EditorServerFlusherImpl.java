package io.core9.editor;

import io.core9.editor.resource.BlockCommandImpl;
import io.core9.editor.resource.BlockTool;
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

		server.use("/plugins/editor/flush", new Middleware() {
			@Override
			public void handle(Request req) {
				String host = req.getVirtualHost().getHostname();
				req.getResponse().end("hi from : " + host);

				BlockTool blockTool = new BlockCommandImpl();
				JSONObject data = new JSONObject();

				data.put("host", ClientRepositoryImpl.cleanHost(host));
				data.put("url", "http://" + host + req.getPath());
				blockTool.setData("data/editor", data);

			}


		});

	}

}
