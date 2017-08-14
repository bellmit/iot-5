package com.cetiti.ddapv2.process.acceptor.httpclient;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.model.Data;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月7日
 * 
 */

@EnableScheduling
public class HttpClient implements SchedulingConfigurer{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClient.class);
	private static final int HTTP_SUCCESS = 200;
	
	private CloseableHttpClient httpClient;
	private List<HttpRequest> requestList;
	
	private Map<String, Object>buildParamMap(HttpRequest request){
		Map<String, Object> params = new HashMap<>();
		if(null!=request.getParamMap()){
			params.putAll(request.getParamMap());
		}
		if(null!=request.getParamSupplier()){
			params.putAll(request.getParamSupplier().get());
		}
		return params;
	}
	
	public String doGet(HttpRequest request){
		if(null==request||!StringUtils.hasText(request.getApiUrl())){
			return null;
		}
		HttpGet httpGet = null;
		try{
			URIBuilder uriBuilder = new URIBuilder(request.getApiUrl());
			for (Entry<String, Object> entry : buildParamMap(request).entrySet()) {
	            uriBuilder.setParameter(entry.getKey(), entry.getValue().toString());
	        }
	        httpGet = new HttpGet(uriBuilder.build().toString());
		}catch (URISyntaxException e) {
			LOGGER.error("apiUrl [{}] illegal.", request.getApiUrl());
			return null;
		}
        
        if(null!=request.getRequestConfig()){
        	httpGet.setConfig(request.getRequestConfig());
        }
        try{
        	CloseableHttpResponse response = this.httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == HTTP_SUCCESS) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (Exception e) {
			LOGGER.error("doGet exception [{}]", e.getMessage());
		}
        
        return null;
	}
	
	public String doPost(HttpRequest request){
		if(null==request||!StringUtils.hasText(request.getApiUrl())){
			return null;
		}
		HttpPost httpPost = new HttpPost(request.getApiUrl());
        if(null!=request.getRequestConfig()){
        	httpPost.setConfig(request.getRequestConfig());
        }
       
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : buildParamMap(request).entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }
        try{
        	UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(list, "UTF-8");
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = this.httpClient.execute(httpPost);
            if(response.getStatusLine().getStatusCode() == HTTP_SUCCESS){
            	return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (Exception e) {
			LOGGER.error("doPost exception [{}]", e.getMessage());
		}
        
        return null;
	}
	
	public Data req(HttpRequest request){
		if(null==request||null==request.getFunction()){
			return null;
		}
		String retn = null;
		if(HttpRequest.METHOD_GET.equals(request.getHttpMethod())){
			retn = doGet(request);
		}else{
			retn = doPost(request);
		}
		if(null==retn){
			return null;
		}
		return request.getFunction().apply(retn);
	}
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		for(final HttpRequest request:requestList){
			if(!StringUtils.hasText(request.getCron())){
				continue;
			}
			taskRegistrar.addTriggerTask(new Runnable() {  
	            @Override  
	            public void run() { 
	            	LOGGER.info("scheduled request [{}]", request);
	                req(request);
	            }  
	        }, new Trigger() {  
	            @Override  
	            public Date nextExecutionTime(TriggerContext triggerContext) {  
	                CronTrigger trigger = new CronTrigger(request.getCron());  
	                Date nextExec = trigger.nextExecutionTime(triggerContext);  
	                return nextExec;  
	            }  
	        });  
		}
	}

	public CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public void setHttpClient(CloseableHttpClient httpClient) {
		this.httpClient = httpClient;
	}

	public List<HttpRequest> getRequestList() {
		return requestList;
	}

	public void setRequestList(List<HttpRequest> requestList) {
		this.requestList = requestList;
	}
	
}
