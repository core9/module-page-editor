package io.core9.editor.client;

import io.core9.editor.FileDataHandler;
import io.core9.editor.FileWidget;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerGlobal;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.events.PluginLoaded;

import com.google.common.io.CharStreams;

@PluginImplementation
public class FileWidgetImpl implements FileWidget {

	private DataHandler<FileDataHandlerConfig> handler;

	@Override
	public void handle(Request request) {
		System.out.println("");
	}

	@Override
	public String getName() {
		return "editor static files";
	}

	@Override
	public String getTemplateName() {
		return "io.core9.editor.file";
	}

	@PluginLoaded
	public void onFileDataHandlerLoaded(FileDataHandler<FileDataHandlerConfig> fileDataHandler) {
		FileDataHandlerConfig options = new FileDataHandlerConfig();
		DataHandlerGlobal<String> ClientID = new DataHandlerGlobal<String>();
		ClientID.setGlobal(true);
		options.setClientId(ClientID);
		this.handler = fileDataHandler.createDataHandler(options);
	}

	@Override
	public DataHandler<?> getDataHandler() {
		return handler;
	}

	@Override
	public String getTemplate() {
		try {
			return CharStreams.toString(new InputStreamReader(this.getClass().getResourceAsStream("/editor/file.soy")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getId() {
		return "C9EDITORFILEWIDGET";
	}

	@Override
	public void setId(String id) {
	}

	@Override
	public Map<String, Object> retrieveDefaultQuery() {
		return null;
	}

	@Override
	public String retrieveCollectionOverride() {
		// TODO Auto-generated method stub
		return null;
	}

}
