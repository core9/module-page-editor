package io.core9.editor.server;

import io.core9.editor.admin.AdminConnector;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorABtestServerImpl implements EditorABtestServer {

	@InjectPlugin
	private Server server;

	@Override
	public void execute() {

		server.use("/abtest/(.*)", new Middleware() {

			@Override
			public void handle(Request req) {
				Map<String, Object> params = req.getParams();

				Map<String, Object> postData = req.getBodyAsMap().toBlocking().last();







				String content = "";


				if (!content.equals("")) {
					req.getResponse().putHeader("Content-Type", "text/html");
					req.getResponse().putHeader("Content-Length", Integer.toString(content.length()));
					// req.getResponse().sendBinary(ByteStreams.toByteArray(res));
					req.getResponse().end(content);

				} else {
					req.getResponse().setStatusCode(404);
					req.getResponse().setStatusMessage("File not found");
					req.getResponse().end();
				}

			}

		});

	}

}
