package com.cetiti.ddapv2.process.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:spring/*.xml")
public class HttpClientTest {
	
	@Resource(name="processHttpClient")
	private HttpClient httpClient;
	@Resource
	private JsonUtil jsonUtil;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testPost() throws Exception {
		Map<String, String> data = new HashMap<>();
		data.put("test", "data00");
		httpClient.post("http://10.0.30.33:8060/api/v1/warndata", jsonUtil.toJson(data));
		Thread.sleep(100000);
	}

}
