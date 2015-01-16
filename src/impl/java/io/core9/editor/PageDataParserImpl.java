package io.core9.editor;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

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
		checkDataFiles();
	}

	private void checkDataFiles() {
		System.out.println("");
	}

	private Map<Integer,BlockData> getAllBlockDataFromDirectory(String directory) {
		Map<Integer, BlockData> results = new HashMap<Integer, BlockData>();
		File[] files = new File(directory).listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if (file.isFile()) {
				int position = Integer.parseInt(file.getName().split("-")[1]);
				BlockData blockData = new BlockDataImpl();
				blockData.addFile(file);
				results.put(position, blockData);
			}
		}
		return results;
	}


	@Override
	public void switchBlockData(int i, int j) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(dataDirectory);
		BlockData orgI = map.get(i);
		BlockData orgJ = map.get(j);
		map.put(i, orgJ);
		map.put(j, orgI);
		updateList(map);
	}

	private void updateList(Map<Integer, BlockData> map) {
		for (Entry<Integer, BlockData> entry : map.entrySet()) {
			BlockData blockData = entry.getValue();
			blockData.setPosition(entry.getKey());
			blockData.save(updateDirectory);
		}

		deleteAllBlockData(dataDirectory);

		Map<Integer, BlockData> updateMap = getAllBlockDataFromDirectory(updateDirectory);
		for (Entry<Integer, BlockData> entry : updateMap.entrySet()) {
			BlockData blockData = entry.getValue();
			blockData.setPosition(entry.getKey());
			blockData.save(dataDirectory);
		}

		deleteAllBlockData(updateDirectory);
	}

	@Override
	public BlockData getBlockData(int i) {
		BlockData dataBlock = null;
		try {
			dataBlock = getAllBlockDataFromDirectory(dataDirectory).get(i);
		} catch (Exception e) {
		}
		return dataBlock;
	}

	@Override
	public void replaceBlock(int i, BlockData blockData) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(dataDirectory);
		map.put(i, blockData);
		updateList(map);
	}

	@Override
	public void appendBlockData(BlockData blockData) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(dataDirectory);
		int size = getNrOfLastBlock(map);
		map.put(size + 1, blockData);
		updateList(map);
	}

	private static Entry<Integer, BlockData> getLastElement(final Set<Entry<Integer, BlockData>> c) {
	    final Iterator<Entry<Integer, BlockData>> itr = c.iterator();
	     Entry<Integer, BlockData> lastElement = itr.next();
	    while(itr.hasNext()) {
	        lastElement=itr.next();
	    }
	    return lastElement;
	}

	private int getNrOfLastBlock(Map<Integer, BlockData> data){
		Set<Entry<Integer, BlockData>> list = data.entrySet();
		return getLastElement(list).getKey();
	}

	@Override
	public void insertBlockData(int i, BlockData blockData) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(dataDirectory);
		Map<Integer, BlockData> newMap = insertIntoMap(map, i, blockData);
		updateList(newMap);
	}

	private Map<Integer, BlockData> insertIntoMap(Map<Integer, BlockData> map, int i, BlockData blockData) {
		Map<Integer, BlockData> newMap = new HashMap<Integer, BlockData>();
		BlockData orgData = null;
		for(Entry<Integer, BlockData> entry : map.entrySet()){
			if(entry.getKey() < i){
				newMap.put(entry.getKey(), entry.getValue());
			}
			if(entry.getKey() == i){
				orgData = map.get(i);
				newMap.put(i, blockData);
			}
			if(entry.getKey() > i){
				newMap.put(entry.getKey() + 1, entry.getValue());
			}
			if(orgData != null){
				orgData.setPosition(i + 1);
				newMap.put(i + 1, orgData);
			}
		}
		return newMap;
	}



	@Override
	public void deleteBlockData(int i) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(dataDirectory);
		Map<Integer, BlockData> newMap = removeFromMap(map, i);
		updateList(newMap);
	}

	private Map<Integer, BlockData> removeFromMap(Map<Integer, BlockData> map, int i) {
		Map<Integer, BlockData> newMap = new HashMap<Integer, BlockData>();
		for(Entry<Integer, BlockData> entry : map.entrySet()){
			if(entry.getKey() < i){
				newMap.put(entry.getKey(), entry.getValue());
			}
			if(entry.getKey() == i){
				newMap.remove(i);
			}
			if(entry.getKey() > i){
				newMap.put(entry.getKey() - 1, entry.getValue());
			}
		}
		return newMap;
	}

	private void deleteAllBlockData(String directory) {
		Map<Integer, BlockData> map = getAllBlockDataFromDirectory(directory);
		for(Entry<Integer, BlockData> entry : map.entrySet()){
			new File(entry.getValue().getFilePath()).delete();
		}
	}

	@Override
	public Map<Integer, BlockData> getAllBlockData() {
		return getAllBlockDataFromDirectory(dataDirectory);
	}

	@Override
	public void deleteBlockData() {
		deleteAllBlockData(dataDirectory);
	}

	@Override
	public String getDataDirectory() {
		return dataDirectory;
	}

}
