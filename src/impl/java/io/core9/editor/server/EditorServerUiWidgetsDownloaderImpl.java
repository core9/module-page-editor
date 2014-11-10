package io.core9.editor.server;

import io.core9.editor.GitHandlerImpl;
import io.core9.plugin.server.Server;
import io.core9.plugin.server.handler.Middleware;
import io.core9.plugin.server.request.Request;

import java.io.File;
import java.io.IOException;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import org.apache.commons.io.FileUtils;

@PluginImplementation
public class EditorServerUiWidgetsDownloaderImpl implements EditorServerUiWidgetsDownloader {

	@InjectPlugin
	private Server server;

	@Override
	public void execute() {

		server.use("/plugins/editor/ui-widgets-downloader(.*)", new Middleware() {
			@Override
			public void handle(Request req) {

				String httpsRepositoryUrl = "https://github.com/core9/core9-ui-widget-filemanager.git";
				String repoDirectory = "data/tmp-dir";

				delete(new File("data/core9-ui-widgets"));

				GitHandlerImpl.clonePublicGitRepository(httpsRepositoryUrl, repoDirectory);

				move(new File(repoDirectory + "/core9-ui-widgets/"), new File("data/core9-ui-widgets/"));

			}
		});
	}

	private void move(File sourceDir, File destinationDir){
        try {
            FileUtils.moveDirectory(sourceDir, destinationDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	private void delete(File f){
		if (f.isDirectory()) {
			for (File c : f.listFiles())
				delete(c);
		}
		if (!f.delete())
			System.out.println("Failed to delete file: " + f);
	}

}
