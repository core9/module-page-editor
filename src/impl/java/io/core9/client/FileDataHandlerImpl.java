package io.core9.client;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.ClientRepositoryImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.FileDataHandler;
import io.core9.editor.RequestImpl;
import io.core9.plugin.admin.plugins.AdminConfigRepository;
import io.core9.plugin.server.request.Request;
import io.core9.plugin.widgets.datahandler.DataHandler;
import io.core9.plugin.widgets.datahandler.DataHandlerFactoryConfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import net.xeoh.plugins.base.annotations.PluginImplementation;
import net.xeoh.plugins.base.annotations.injections.InjectPlugin;

import com.google.common.io.ByteStreams;

@PluginImplementation
public class FileDataHandlerImpl implements FileDataHandler<FileDataHandlerConfig> {

	private static final String pathPrefix = "data/editor";

	@InjectPlugin
	private AdminConfigRepository configRepository;

	@Override
	public String getName() {
		return "editor_client_file_data_handler";
	}

	@Override
	public Class<? extends DataHandlerFactoryConfig> getConfigClass() {
		return FileDataHandlerConfig.class;
	}

    static String urlEncodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new UnsupportedOperationException(e);
        }
    }
    static String urlEncodeUTF8(Map<?,?> map) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                urlEncodeUTF8(entry.getKey().toString()),
                urlEncodeUTF8(entry.getValue().toString())
            ));
        }
        return sb.toString();
    }

	@Override
	public DataHandler<FileDataHandlerConfig> createDataHandler(final DataHandlerFactoryConfig options) {
		return new DataHandler<FileDataHandlerConfig>() {

			private ClientRepositoryImpl clientRepository;
			private String contentType;

			@Override
			public Map<String, Object> handle(Request req) {

				AssetsManager assetsManager = new AssetsManagerImpl(pathPrefix);
				clientRepository = new ClientRepositoryImpl();
				
				clientRepository.addDomain("www.easydrain.nl", "easydrain");
				clientRepository.addDomain("easydrain.docker.trimm.net", "easydrain");
				clientRepository.addDomain("easydrain.localhost", "easydrain");
				clientRepository.addDomain("www.kennispark.nl", "kennispark");
				clientRepository.addDomain("kennispark.editor.docker.trimm.net", "kennispark");
				clientRepository.addDomain("kennispark.localhost", "kennispark");
				
				
				EditorRequest request = new RequestImpl();
				request.setClientRepository(clientRepository);

				String absoluteUrl = "http://" + req.getHostname() + req.getPath() + "?" + urlEncodeUTF8(req.getParams());
				request.setAbsoluteUrl(absoluteUrl);


				assetsManager.setRequest(request);

				Map<String, Object> result = new HashMap<String, Object>();
				Map<String, Object> menu = configRepository.readConfig(req.getVirtualHost(), ((FileDataHandlerConfig) options).getClientId(req));
				if (menu == null) {
					menu = new HashMap<String, Object>();
				}

				String path = req.getPath();

				try {
					contentType = Files.probeContentType(Paths.get(path));
				} catch (IOException e2) {
					e2.printStackTrace();
				}

				String file = assetsManager.getStaticFilePath(path);

				InputStream res = fileToBinary(file);



				try {
					if (new File(file).exists()) {
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



				return result;
			}

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
			public FileDataHandlerConfig getOptions() {
				return (FileDataHandlerConfig) options;
			}
		};
	}
}
