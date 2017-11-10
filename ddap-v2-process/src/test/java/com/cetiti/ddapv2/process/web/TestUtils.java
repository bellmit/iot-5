package com.cetiti.ddapv2.process.web;

import org.springframework.util.StringUtils;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月18日
 * 
 */
public class TestUtils {
	
	public static final String key = "ukcb96f4be242e891ff80e6e6c3805681c";
	public static final String secret = "us46f8d57022061b570c1897901ea869f2";
	
	public static final String baseUrl = "http://10.70.7.72:8080/";
	//public static final String baseUrl = "http://10.0.30.33:8080/";
	
	public static boolean isSuccessResult(String restResult){
		return StringUtils.hasText(restResult)&&restResult.indexOf("\"code\":\"0\"")>-1;
	}
}
