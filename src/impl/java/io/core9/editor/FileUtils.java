package io.core9.editor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class FileUtils {

	private static Logger logger = Logger.getLogger(FileUtils.class.getName());

	public static String readPathToString(Path file) {
		BufferedReader reader = null;
		try {
			reader = Files.newBufferedReader(file, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		StringBuilder content = new StringBuilder();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				content.append(line);//.append("/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Content: " + content.toString());
		return content.toString();//.substring(0, content.toString().length() - 2);
	}

	public static Path writeToFile(Path file, String string) {
		try {
			Files.write(file, ImmutableList.of(string), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return file;
	}

	public static Path getFile(String path, String filename) {
		// For a simple file system with Unix-style paths and behavior:
		FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
		Path foo = fs.getPath(path); // "/foo"
		try {
			Files.createDirectory(foo);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return foo.resolve(filename); // /foo/new-front-page.html
	}


	public static void writeToFile(String fileName, String content) {
		File targetFile = new File(fileName);
		File parent = targetFile.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
			writer.write(content);
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				logger.log(Level.INFO, e.getMessage());
			}
		}
	}

	public static void createDirectory(String directory) {
		if (!new File(directory).exists()) {
			new File(directory).mkdirs();
		}
	}

	public static String readFile(String path, Charset encoding) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(path));
		} catch (IOException e) {
			logger.log(Level.INFO, e.getMessage());
		}
		return new String(encoded, encoding);
	}

	public static boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}
}
