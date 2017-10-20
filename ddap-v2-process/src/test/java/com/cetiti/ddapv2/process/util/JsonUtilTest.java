package com.cetiti.ddapv2.process.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class JsonUtilTest {
	
	private JsonUtil jsonUtil = new JsonUtil();

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testToJson() {
		Map<String, String> map = new HashMap<>();
		map.put("abc", "123");
		map.put("cef", "456");
		String mjson = jsonUtil.toJson(map);
		Map<String, Object> jmap = jsonUtil.mapFromJson(mjson, String.class, Object.class);
		System.out.println(jmap);
	}

	@Test
	public void testBeanFromJson() {
		fail("Not yet implemented");
	}

	@Test
	public void testListFromJson() {
		fail("Not yet implemented");
	}

}
