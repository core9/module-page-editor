package io.core9.editor.admin;

import java.io.File;

public class CouldNotCreateDirectory extends Exception {

	public CouldNotCreateDirectory(File baseDir) {
		System.out.println("Could not create directory : " + baseDir.getAbsolutePath());
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

}
