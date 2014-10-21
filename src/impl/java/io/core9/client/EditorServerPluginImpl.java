package io.core9.client;

import io.core9.editor.EditorServerPlugin;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;

import com.google.common.io.CharStreams;

@PluginImplementation
public class EditorServerPluginImpl implements EditorServerPlugin {

	@Override
	public void handle(Request request) {
		
		
	}

	@Override
	public String getName() {
		return "editor_server_widget";
	}

	@Override
	public String getTemplateName() {
		return "io.core9.editor.editor";
	}

	@Override
	public DataHandler<?> getDataHandler() {

		DataHandler<?> handler = new DataHandler<DataHandlerFactoryConfig>() {

			@Override
			public Map<String, Object> handle(Request req) {
				Map<String, Object> result = new HashMap<String, Object>();
				return result ;
			}

			@Override
			public DataHandlerFactoryConfig getOptions() {
				DataHandlerFactoryConfig options = new DataHandlerFactoryConfig() {
					
					private String componentName;

					@Override
					public void setComponentName(String componentName) {
						this.componentName = componentName;
					}
					
					@Override
					public String getComponentName() {
						return componentName;
					}
				};
				return options ;
			}
		};
		return handler ;
	}

	@Override
	public String getTemplate() {
		try {
			return CharStreams.toString(new InputStreamReader(this.getClass().getResourceAsStream("/editor/editor.soy")));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getId() {
		return "C9EDITOREDITORWIDGET";
	}

	@Override
	public void setId(String id) {
		
		
	}

	@Override
	public Map<String, Object> retrieveDefaultQuery() {
		
		return null;
	}



}
