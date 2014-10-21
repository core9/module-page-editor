package io.core9.editor;

import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;
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
			public void handle(Request request) {
				request.getResponse().end("hi from : " + request.getVirtualHost().getHostname());
			}
		});
		
		
	}

}
