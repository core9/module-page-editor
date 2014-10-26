package io.core9.editor;

import java.io.File;

public class BlockDataImpl implements BlockData {

	private File file;

	@Override
	public void addFile(File file) {
		this.file = file;
	}

	@Override
	public String getFile() {
		return file.getPath();
	}

}
