package com.cetiti.ddapv2.process.handler.json;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.cetiti.ddapv2.process.util.JsonUtil;

public class DefaultTcpServerHandlerTest {

	@Before
	public void setUp() throws Exception {
	}
	JsonUtil jsonUti = new JsonUtil();

	@Test
	public void test() {
		List<Map<String, Object>> maplist = new ArrayList<>();
		for(int i=10; i>0; i--){
			Map<String, Object> map = new HashMap<>();
			if(i%2==0){
				map.put("deviceId", "did"+System.currentTimeMillis());
			}else {
				map.put("serialNumber", "sn"+System.currentTimeMillis());
				map.put("productId", "pd"+i);
			}
			map.put("testdata", System.currentTimeMillis());
			System.out.println(jsonUti.toJson(map));
			maplist.add(map);
		}
		System.out.println(jsonUti.toJson(maplist));
		
	}

}
