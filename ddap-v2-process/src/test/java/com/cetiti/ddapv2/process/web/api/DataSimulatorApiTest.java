package com.cetiti.ddapv2.process.web.api;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.cetiti.ddapv2.process.web.TestUtils;

public class DataSimulatorApiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testUploadData() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> data = new HashMap<>();
		data.put("deviceId", "D1505467235604");
		data.put("temperature", 50);
		data.put("humidity", 90);
		String retn = restTemplate.postForObject(TestUtils.baseUrl+"/api/simulator/v1/upload", data, String.class);
		//System.out.println(retn);
	}
	
	@Ignore
	public void testWarnData() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> data = new HashMap<>();
		data.put("deviceId", "123456");
		data.put("data", System.currentTimeMillis());
		String retn = restTemplate.postForObject(TestUtils.baseUrl+"/api/simulator/v1/warndata", data, String.class);
		//System.out.println(retn); http://10.70.7.72:8090/api/simulator/v1/warndata
	}
	
	@Test
	public void testHttpAcceptor() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> data = new HashMap<>();
		data.put("deviceId", "D1505467235604");
		data.put("temperature", 50);
		data.put("humidity", 90);
		String retn = restTemplate.postForObject(TestUtils.baseUrl+"/api/acceptor/http/easylink", data, String.class);
		System.out.println(retn);
	}

}
