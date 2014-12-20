package io.core9.editor.jctree;

import org.junit.Test;

import com.googlecode.jctree.ArrayListTree;
import com.googlecode.jctree.NodeNotFoundException;

public class TestJcTree {

	@Test
	public void testJcTree() throws NodeNotFoundException {

		ArrayListTree<String> tree = new ArrayListTree<String>();
		tree.add("Level-1");
		tree.add("Level-1", "Level-11");
		tree.add("Level-1", "Level-12");
		tree.add("Level-1", "Level-13");
		tree.add("Level-12", "Level-121");
		tree.add("Level-12", "Level-122");
		tree.add("Level-122", "Level-1221");
		tree.add("Level-13", "Level-131");
		tree.add("Level-13", "Level-132");
		tree.add("Level-13", "Level-133");
		tree.add("Level-11", "Level-111");
		tree.add("Level-11", "Level-112");

		System.out.println(tree.parent("Level-121"));
		System.out.println(tree.children("Level-12"));

	}

}
