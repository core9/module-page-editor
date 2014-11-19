package io.core9.editor.admin;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.data.ClientData;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.IOException;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
public class EditorAdminServerImpl implements EditorAdminServer {

	@InjectPlugin
	private Server server;



	@Override
	public void execute() {

		server.use("/ui-admin/(.*)", new Middleware() {



			@Override
			public void handle(Request req) {

				String host = req.getVirtualHost().getHostname();
				
				EditorRequest editorRequest = new EditorRequestImpl();
				editorRequest.setClientRepository(ClientData.getRepository());
				editorRequest.setAbsoluteUrl("http://" + host + req.getPath());
				
				AssetsManager assetsManager = new AssetsManagerImpl("data/editor", editorRequest);
				
				String clientId = assetsManager.getClientId();
				
				String baseDir = "data/editor/" + clientId;
				
				Map<String, Object> postData = req.getBodyAsMap().toBlocking().last();
				
				
				
				Map<String, Object> params = req.getParams();

				FileManager fm;
				FileManagerRequest request = new FileManagerRequest();
				String result = "";
				try {
					fm = new FileManagerImpl(baseDir);
					request.setOperation((String) params.get("operation"));
					request.setId((String) params.get("id"));
					request.setName((String) params.get("text"));
					request.setParent((String) params.get("parent"));
					request.setType((String) params.get("type"));
					if(postData != null && postData.get("content") != null){
						request.setContent((String) postData.get("content"));
					}
					result = fm.action(request);
				} catch (CouldNotCreateDirectory e2) {
					e2.printStackTrace();
				} catch (IOException e2) {
					e2.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (!result.equals("")) {
					req.getResponse().putHeader("Content-Type", "application/json");
					req.getResponse().putHeader("Content-Length", Integer.toString(result.length()));
					req.getResponse().end(result);

				} else {
					req.getResponse().setStatusCode(404);
					req.getResponse().setStatusMessage("File not found");
					req.getResponse().end();
				}

			}

		});

	}

}
