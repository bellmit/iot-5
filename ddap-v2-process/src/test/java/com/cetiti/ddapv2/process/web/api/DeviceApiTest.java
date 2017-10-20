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

import com.cetiti.ddapv2.process.web.ApiAssistParams;
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
		/*SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("localhost", 8888));
		httpRequestFactory.setProxy(proxy);
		RestTemplate restTemplate = new RestTemplate(httpRequestFactory);  */
		RestTemplate restTemplate = new RestTemplate(); 
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        ApiAssistParams params = new ApiAssistParams();
        params.setKey("ukcb96f4be242e891ff80e6e6c3805681c");
        params.setTimestamp(System.currentTimeMillis());
        //params.setTimestamp(123456);
        Map<String, Object> map = new HashMap<>();
        //map.put("id", "D1505467235604");
        map.put("descAttributes", "科技");
        params.setParams(map);
        map.put("pageNum", 1);
		map.put("pageSize", 10);
        params.setSign(params.generateSign("us46f8d57022061b570c1897901ea869f2"));
       
        for(Entry<String, Object> entry:params.toMap().entrySet()){
        	form.add(entry.getKey(), entry.getValue().toString());
        }
       
        String result = restTemplate.postForObject(TestUtils.baseUrl+"/api/device/v1/list", form, String.class);  
        System.out.println(result);
        assertTrue(TestUtils.isSuccessResult(result));
	}

}
