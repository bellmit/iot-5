package com.cetiti.ddapv2.process.util;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月21日
 * 
 */
public class RequestMessageContext {
	
	private static ThreadLocal<String> msgContext = new ThreadLocal<>();
	
	public static void setMsg(String msg) {
		msgContext.set(msg);
	}
	
	public static String getMsg() {
		return msgContext.get();
	}
}
