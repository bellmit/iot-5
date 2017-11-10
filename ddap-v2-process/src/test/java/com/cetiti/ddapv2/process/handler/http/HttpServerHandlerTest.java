package com.cetiti.ddapv2.process.handler.http;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.PublicImpl;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.client.RestTemplate;

import com.cetiti.ddapv2.process.spi.Codec;
import com.cetiti.ddapv2.process.spi.easylinkin.LoraData;
import com.cetiti.ddapv2.process.spi.easylinkin.SmokeSensor;
import com.cetiti.ddapv2.process.util.JsonUtil;
import com.mysql.fabric.xmlrpc.base.Data;

public class HttpServerHandlerTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void test() {
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> data = new HashMap<>();
		data.put("deviceId", "D1505467235604");
		data.put("temperature", 50);
		data.put("humidity", 90);
		LoraData loraData = new LoraData();
		loraData.setMac("12456");
		loraData.setAppeui("313213");
		loraData.setData("10");
		String retn = restTemplate.postForObject("http://101.68.88.222:8188/easylinkin", loraData, String.class);
		System.out.println(retn);
		
		Map<String, Codec<?, ?>> map = new HashMap<>();
		map.put("123", new SmokeSensor());
		//map.put("123", new Data());
	}
	
	@Test
	public void testParseJson(){
		JsonUtil jsonUtil = new JsonUtil();
		String json = "{\"mac\":\"004a770124000725\",\"appeui\":\"2c26c5012483a00d\",\"last_update_time\":\"20171016113011\",\"data\":\"64ffe60200ff\",\"reserver\":\"null\",\"data_type\":223,\"gateways\":null}";
		LoraData lora = jsonUtil.beanFromJson(json, LoraData.class);
		System.out.println(lora);
	}

}
