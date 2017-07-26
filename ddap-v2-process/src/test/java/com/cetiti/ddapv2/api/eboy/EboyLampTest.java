package com.cetiti.ddapv2.api.eboy;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import jdk.nashorn.internal.ir.annotations.Ignore;


/**
 * 意博智能灯接口测试
 * @author Wuwuhao
 * 
 */
public class EboyLampTest {
	
	private String baseUrl = "http://183.129.184.212:8081/site/light.php/exinterface/";
	//private CloseableHttpClient httpclient = HttpClients.createDefault();
	
	
	public String post(String url, Map<String, String> params) {
		
		HttpHost proxy = new HttpHost("localhost", 8888);
		CloseableHttpClient httpclient = HttpClients.custom().setProxy(proxy).build();
		
		HttpPost httpPost = new HttpPost(url);		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		for(Entry<String, String> entry:params.entrySet()){
			nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		}

		String retn = null;
		CloseableHttpResponse response = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps));
			response = httpclient.execute(httpPost);
			HttpEntity entity = response.getEntity();
		    retn = EntityUtils.toString(entity);
		    System.out.println(retn);
			int code = response.getStatusLine().getStatusCode();
			assertTrue(code<300);
		    
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		finally {
			try {
				response.close();
			} catch (Exception e2) {
				
			}  
		}
		
		return retn;
	}
	
	private Map<String, String> buildParamMap(){
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", "admin");
		params.put("password", "123456");
		return params;
	}
	
	@Ignore
	public void testGetRTUlist() {
		Map<String, String> params = buildParamMap();
		params.put("command", "getrtulist");
		
		post(baseUrl+"getRTUlist", params);
	}
	
	@Test
	public void testGetLamplist() {
		Map<String, String> params = buildParamMap();
		params.put("rtuid", "794");
		params.put("command", "getlamplist");
		
		post(baseUrl+"getLamplist", params);
	}
	
	@Ignore
	public void testGetLampInfo() {
		Map<String, String> params = buildParamMap();
		params.put("lampid", "2707/794");
		params.put("command", "get");
		
		post(baseUrl+"getLampInfo", params);
	}
	
	@Ignore
	public void testGetRTUInfo() {
		Map<String, String> params = buildParamMap();
		params.put("rtuid", "792");
		params.put("command", "get");
		
		post(baseUrl+"getRTUInfo", params);
	}
	
	@Ignore
	public void testCircuitControlLamp() {
		Map<String, String> params = buildParamMap();
		params.put("circuitid", "001/792");
		params.put("command", "close");
		
		post(baseUrl+"circuitControlLamp", params);
	}
	
	@Ignore
	public void testRTUcontrolLamp() {
		Map<String, String> params = buildParamMap();
		params.put("rtuid", "792");
		params.put("command", "close");
		
		post(baseUrl+"RTUcontrolLamp", params);
	}
	
	@Ignore
	public void testGroupControlLamp() {
		Map<String, String> params = buildParamMap();
		params.put("groupid", "1/792");
		params.put("command", "close");
		
		post(baseUrl+"groupControlLamp", params);
	}
	
	@Ignore
	public void testLampControl() {
		Map<String, String> params = buildParamMap();
		params.put("lampid", "2707");
		params.put("command", "close");
		
		post(baseUrl+"LampControl", params);
	}
}
