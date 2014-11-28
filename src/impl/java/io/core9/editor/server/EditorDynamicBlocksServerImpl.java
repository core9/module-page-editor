package io.core9.editor.server;

import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import com.google.common.io.ByteStreams;

@PluginImplementation
public class EditorDynamicBlocksServerImpl implements EditorDynamicBlocksServer {

	@InjectPlugin
	private Server server;

	private InputStream fileToBinary(String filename) {
		InputStream res = null;
		try {
			res = new FileInputStream(new File(filename));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public void execute() {

		server.use("/dynamic-blocks/(.*)", new Middleware() {


			@Override
			public void handle(Request req) {
				String contentType = "";
				String resourceFile = "";
				String requestPath = req.getPath().replace("/dynamic-blocks", "");


				resourceFile = "data/dynamic-data" + requestPath;


				try {
					contentType = Files.probeContentType(Paths.get(resourceFile));
				} catch (IOException e) {
					e.printStackTrace();
				}

				InputStream res = null;
				res = fileToBinary(resourceFile);

				try {
					if (new File(resourceFile).exists()) {
						req.getResponse().putHeader("Content-Type", contentType);
						req.getResponse().putHeader("Content-Length", Integer.toString(res.available()));
						req.getResponse().sendBinary(ByteStreams.toByteArray(res));
						req.getResponse().end();

					} else {
						req.getResponse().setStatusCode(404);
						req.getResponse().setStatusMessage("File not found");
						req.getResponse().end();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					res.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

	}

}
