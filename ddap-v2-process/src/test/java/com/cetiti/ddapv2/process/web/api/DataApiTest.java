package com.cetiti.ddapv2.process.web.api;

import static org.junit.Assert.*;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cetiti.ddapv2.process.web.ApiParamsBuilder;
import com.cetiti.ddapv2.process.web.TestUtils;

public class DataApiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testGetLatestData() {
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888));
		httpRequestFactory.setProxy(proxy);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);  
		
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", "D1505467235604");
       
        for(Entry<String, Object> entry:builder.build(map).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/data/v1/latest", form, String.class);  
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

	@Test
	public void testGetDevice() {
		SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888));
		httpRequestFactory.setProxy(proxy);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);  
		
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", "D1505467235604");
        map.put("pageNum", 1);
        map.put("pageSize", 10);
        map.put("startTime", "2017-09-20 00:00:00");
        map.put("endTime", "2017-10-20 00:00:00");
       
        for(Entry<String, Object> entry:builder.build(map).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/data/v1/history", form, String.class);
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

}
