package io.core9.editor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;

public class FileUtils {

	public static String readFileToString(Path file) {
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
				content.append(line).append("/n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println("Content: " + content.toString());
		return content.toString();
	}

	public static Path writeToFile(Path file, String string) {
		try {
			Files.write(file, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
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

}
