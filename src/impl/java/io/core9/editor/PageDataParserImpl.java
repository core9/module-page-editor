package io.core9.editor;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PageDataParserImpl implements PageDataParser {

	@SuppressWarnings("unused")
	private String pageFilePath;
	private String dataDirectory;
	private String updateDirectory;

	public PageDataParserImpl(String pageFilePath) {
		this.pageFilePath = pageFilePath;
		this.dataDirectory = pageFilePath.replace("template.html", "data");
		this.updateDirectory = dataDirectory + File.separator + "updates";
		checkEnvironment();
	}

	private void checkEnvironment() {
		File targetFile = new File(updateDirectory);
		File parent = targetFile.getParentFile();
		if (!parent.exists() && !parent.mkdirs()) {
			throw new IllegalStateException("Couldn't create dir: " + parent);
		}
		targetFile.mkdir();
	}

	private List<BlockData> getAllBlockDataFromDirectory(String directory) {
		List<BlockData> results = new ArrayList<BlockData>();
		File[] files = new File(directory).listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if (file.isFile()) {
				BlockData blockData = new BlockDataImpl();
				blockData.addFile(file);
				results.add(blockData);
			}
		}
		return results;
	}

	@Override
	public void switchBlockData(int i, int j) {
		List<BlockData> list = getAllBlockDataFromDirectory(dataDirectory);
		list.set(i, list.set(j, list.get(i)));
		updateList(list);
	}

	private void updateList(List<BlockData> list) {
		int i = 0;
		for (BlockData blockData : list) {
			blockData.setPosition(i);
			blockData.save(updateDirectory);
			i++;
		}
		deleteAllBlockData(dataDirectory);
		List<BlockData> updateList = getAllBlockDataFromDirectory(updateDirectory);
		int x = 0;
		for (BlockData blockData : updateList) {
			blockData.setPosition(x);
			blockData.save(dataDirectory);
			x++;
		}
		deleteAllBlockData(updateDirectory);
	}

	@Override
	public BlockData getBlockData(int i) {
		return getAllBlockDataFromDirectory(dataDirectory).get(i);
	}

	@Override
	public void replaceBlock(int i, BlockData blockData) {
		List<BlockData> list = getAllBlockDataFromDirectory(dataDirectory);
		list.set(i, blockData);
		updateList(list);
	}


	@Override
	public void appendBlockData(BlockData blockData) {
		List<BlockData> list = getAllBlockDataFromDirectory(dataDirectory);
		list.add(blockData);
		updateList(list);
	}

	@Override
	public void insertBlockData(int i, BlockData blockData) {
		List<BlockData> list = getAllBlockDataFromDirectory(dataDirectory);
		list.add(i, blockData);
		updateList(list);
	}

	@Override
	public void deleteBlockData(int i) {
		List<BlockData> list = getAllBlockDataFromDirectory(dataDirectory);
		list.remove(i);
		updateList(list);
	}

	@Override
	public void deleteAllBlockData(String directory) {
		List<BlockData> list = getAllBlockDataFromDirectory(directory);
		for (BlockData blockData : list) {
			blockData.getFile().delete();
		}
	}

	public List<BlockData> getAllBlockData() {
		return getAllBlockDataFromDirectory(dataDirectory);
	}

}
