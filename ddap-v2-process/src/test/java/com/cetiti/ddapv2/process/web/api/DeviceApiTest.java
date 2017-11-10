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
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import com.cetiti.ddapv2.process.web.ApiParamsBuilder;
import com.cetiti.ddapv2.process.web.TestUtils;


public class DeviceApiTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testGetProduct() {
		RestTemplate restTemplate = new RestTemplate();  
        MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
        ApiAssistParams params = new ApiAssistParams();
        params.setKey("ukcb96f4be242e891ff80e6e6c3805681c");
        params.setTimestamp(System.currentTimeMillis());
        Map<String, Object> map = new HashMap<>();
        //map.put("deviceId", "D1505467235604");
        params.setParams(map);
        params.setSign(params.generateSign("us46f8d57022061b570c1897901ea869f2"));
        form.setAll(params.toMap());
        String url = TestUtils.baseUrl+"/api/device/v1/list?key={key}&sign={sign}&timestamp={timestamp}";
        String result = restTemplate.getForObject(url, String.class, params.toMap());  
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}
	
	@Test
	public void testPostProduct() {
		
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        ApiParamsBuilder builder = new ApiParamsBuilder("uk508595d7ec170d0c3392083e25411056", "us41db5af8b5e973d658e0d22fda570aac");
        Map<String, Object> params = new HashMap<>();
        params.put("productId", "PHIK82CROSSING");
        params.put("pageNum", 1);
		params.put("pageSize", 10);
        
        for(Entry<String, Object> entry:builder.build(params).entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/device/v1/list", form, String.class);  
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

}
