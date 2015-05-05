package io.core9.editor.server;

import io.core9.editor.admin.AdminConnector;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorDynamicBlocksServerImpl implements EditorDynamicBlocksServer {

	@InjectPlugin
	private Server server;

	@Override
	public void execute() {

		server.use("/dynamic-blocks/(.*)", new Middleware() {

			@Override
			public void handle(Request req) {
				Map<String, String> params = req.getParams();

				Map<String, Object> postData = req.getBodyAsMap().toBlocking().last();


				System.out.println(params);


				String requestPath = req.getPath().replace("/dynamic-blocks", "");

				String contentType = "application/javascript";
				if(requestPath.endsWith(".json")){
					contentType = "application/json";
				}

				if(requestPath.startsWith("/update/")){
					AdminConnector.updateDynamicContentType(postData);
					return;
				}

				//



				String[] requestParts = requestPath.split("/");

				String dynamicContentType = requestParts[1];
				String fileName = requestParts[3];





				String content = "";
				try {
					content = AdminConnector.getContentOfFile(requestPath, dynamicContentType, fileName, (String) params.get("id"));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println(content);

				if (!content.equals("")) {
					req.getResponse().putHeader("Content-Type", contentType);
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
