package com.cetiti.ddapv2.process.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月18日
 * 
 */
@Configuration
@PropertySource("classpath:message.properties")
public class MessageUtil {
	
	@Autowired
	Environment env;
	
	public String get(String key){
		if(null==key){
			return null;
		}
		
		return env.getProperty(key, key);	
	}
	
	public String get(String key, String param){
		if(null==key){
			return null;
		}
		String value = env.getProperty(key);
		if(null==value){
			return param + " " + key;
		}else{
			return String.format(value, param);
		}
	}

}
