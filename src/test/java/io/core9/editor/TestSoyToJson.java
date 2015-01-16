package io.core9.editor;

import org.junit.Test;

import com.google.template.soy.data.SoyMapData;

public class TestSoyToJson {

	@SuppressWarnings("unused")
	@Test
	public void test() {
		String goodJson = "{\"url\":\"http://youtu.be/kDfw4yt554g\",\"height\":\"315\",\"width\":\"560\"}";
		String badJson = "{\"url\":\"http://youtu.be/kDfw4yt554g\",\"height\":\"315\",\"width\":\"560\",\"age\":21}";


		//SoyMapData soyData = (SoyMapData) JsonSoyUtils.JsonToSoy(goodJson);
		SoyMapData soyData = (SoyMapData) JsonSoyUtils.JsonToSoy(badJson);
		System.out.println(soyData);
	}

}
