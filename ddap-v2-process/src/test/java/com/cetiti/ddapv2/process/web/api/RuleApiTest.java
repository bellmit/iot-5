package com.cetiti.ddapv2.process.web.api;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.cetiti.ddapv2.process.web.ApiParamsBuilder;
import com.cetiti.ddapv2.process.web.TestUtils;

public class RuleApiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddRule() {
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        map.put("deviceId", "D1505467235604");
        map.put("expression", "temperature<=44&&humidity>56");
        map.put("productId", "P1504784699718");
        map.put("name", "requestbody");
        map.put("description", "apiruleDescription");
       
        for(Entry<String, Object> entry:builder.build(map).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/rule/v1/new", form, String.class);
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

	@Ignore
	public void testDeleteRule() {
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        map.put("ruleId", "R1506563374329");
       
       
        for(Entry<String, Object> entry:builder.build(map).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/rule/v1/delete", form, String.class);
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

	@Ignore
	public void testUpdateRule() {
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        map.put("id", "R1506590404643");
        map.put("deviceId", "D1505467235604");
        map.put("expression", "temperature<=45&&humidity>55");
        map.put("productId", "P1504784699718u");
        map.put("name", "apiruleRequestBody");
        map.put("description", "apiruleDescriptionu");
       
        for(Entry<String, Object> entry:builder.build(map).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/rule/v1/update", form, String.class);
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

	@Ignore
	public void testGetRule() {
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        
        ApiParamsBuilder builder = new ApiParamsBuilder(TestUtils.key, TestUtils.secret);
        Map<String, Object> map = new HashMap<>();
        //map.put("id", "R1506563374329");
        map.put("pageNum", 1);
		map.put("pageSize", 11);
		//map.put("updateTime", "2017-10-01 00:00:00");
        map = builder.build(map);
        for(Entry<String, Object> entry:map.entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/rule/v1/list", form, String.class);
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

}
