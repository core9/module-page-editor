package io.core9.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import io.core9.editor.AssetsManager;
import io.core9.editor.AssetsManagerImpl;
import io.core9.editor.EditorRequest;
import io.core9.editor.EditorRequestImpl;
import io.core9.editor.data.ClientData;
import net.minidev.json.JSONObject;

public class LoadClientSiteTemplatesAndKeepData implements BlockTool {

	@Override
	public String getResponse() {
		return null;
	}

	@Override
	public void setData(String prefix, JSONObject data) {


		String oldTemplatePath = getTemplatePath(prefix, data);
		String newTemplatePath = getTemplatePath("data/temp-editor", data);

		copyFile(newTemplatePath, oldTemplatePath, Boolean.TRUE);

		System.out.println("");
	}

	private String getTemplatePath(String prefix, JSONObject data){
		EditorRequest request = new EditorRequestImpl();
		String absoluteUrl = data.getAsString("url");
		request.setAbsoluteUrl(absoluteUrl);
		request.setClientRepository(ClientData.getRepository());
		AssetsManager assetsManager = new AssetsManagerImpl(prefix, request);
		assetsManager.deleteClientDirectory();
		assetsManager.createSiteDirectory();
		assetsManager.clonePublicSiteFromGit(ClientData.getRepository().getSiteRepository(assetsManager.getClientId()));

		try {
			List<String> blockRepos = ClientData.getRepository().getBlockRepository(assetsManager.getClientId());
			for(String repo : blockRepos){
				assetsManager.cloneBlocksFromGit(repo);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return assetsManager.getPageTemplatePath();
	}

	public static void copyFile(String from, String to) {
        copyFile(from, to, Boolean.FALSE);
    }

    public static void copyFile(String from, String to, Boolean overwrite) {

        try {
            File fromFile = new File(from);
            File toFile = new File(to);

            if (!fromFile.exists()) {
                throw new IOException("File not found: " + from);
            }
            if (!fromFile.isFile()) {
                throw new IOException("Can't copy directories: " + from);
            }
            if (!fromFile.canRead()) {
                throw new IOException("Can't read file: " + from);
            }

            if (toFile.isDirectory()) {
                toFile = new File(toFile, fromFile.getName());
            }

            if (toFile.exists() && !overwrite) {
                throw new IOException("File already exists.");
            } else {
                String parent = toFile.getParent();
                if (parent == null) {
                    parent = System.getProperty("user.dir");
                }
                File dir = new File(parent);
                if (!dir.exists()) {
                    throw new IOException("Destination directory does not exist: " + parent);
                }
                if (dir.isFile()) {
                    throw new IOException("Destination is not a valid directory: " + parent);
                }
                if (!dir.canWrite()) {
                    throw new IOException("Can't write on destination: " + parent);
                }
            }

            FileInputStream fis = null;
            FileOutputStream fos = null;
            try {

                fis = new FileInputStream(fromFile);
                fos = new FileOutputStream(toFile);
                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = fis.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }

            } finally {
                if (from != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                      System.out.println(e);
                    }
                }
                if (to != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Problems when copying file.");
        }
    }

}
