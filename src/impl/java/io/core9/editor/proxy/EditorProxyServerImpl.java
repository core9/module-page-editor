package io.core9.editor.proxy;

import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.IOException;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

@PluginImplementation
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



				String url = "http://localhost.fopshop.nl:8090/ov/carnaval/QE74NAWWHET3";
				Document doc = null;
				try {
					doc = Jsoup.connect(url).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}




				String content = doc.toString();

				String testData = "<article class=\"core9-block smallphoto right\" data-type=\"smallphoto\">";
						testData += "<div>";
						testData += "<img src=\"http://www.easydrain.nl/media/wysiwyg/Homepage/About/Refences/markthal-rotterdam-01.jpg\">";
						testData += "</div>";
						testData += "</article>";

				content = content.replace("</body>", testData + "<script src=\"/ui-widgets/widgets/inpage-context-menu/child/context-menu.js\"></script></body>");


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
