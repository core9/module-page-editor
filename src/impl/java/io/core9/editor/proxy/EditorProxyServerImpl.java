package io.core9.editor.proxy;

import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.util.Map;

import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

public class EditorProxyServerImpl implements EditorProxyServer{

	@InjectPlugin
	private Server server;

	@Override
	public void execute() {

		server.use("/proxy/(.*)", new Middleware() {

			@Override
			public void handle(Request req) {
				@SuppressWarnings("unused")
				Map<String, Object> params = req.getParams();

				@SuppressWarnings("unused")
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
