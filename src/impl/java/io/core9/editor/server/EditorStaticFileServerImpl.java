package io.core9.editor.server;

import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import com.google.common.io.ByteStreams;

@PluginImplementation
public class EditorStaticFileServerImpl implements EditorStaticFileServer {

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

		server.use("/plugins/editor/static/(.*)", new Middleware() {
			private String contentType;
			private String resourceFile;

			@Override
			public void handle(Request req) {

				String requestPath = req.getPath().replace("/plugins/editor/static", "");

				URL resource = EditorStaticFileServerImpl.class.getResource("/");
				try {
					resourceFile = Paths.get(resource.toURI()).toFile().getPath() + "/editor/assets" + requestPath;
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}

				try {
					contentType = Files.probeContentType(Paths.get(resourceFile));
				} catch (IOException e) {
					e.printStackTrace();
				}

				InputStream res = fileToBinary(resourceFile);

				try {
					if (new File(resourceFile).exists()) {
						req.getResponse().putHeader("Content-Type", contentType);
						req.getResponse().putHeader("Content-Length", Integer.toString(res.available()));
						req.getResponse().sendBinary(ByteStreams.toByteArray(res));
						res.close();
					} else {
						req.getResponse().setStatusCode(404);
						req.getResponse().setStatusMessage("File not found");
						req.getResponse().end();
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}

		});

	}

}
