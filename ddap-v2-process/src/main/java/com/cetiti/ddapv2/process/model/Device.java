package com.cetiti.ddapv2.process.model;

import java.util.Map;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public class Device extends Thing{
	
	public static final char STATUS_ONLINE = 'O';
	public static final char STATUS_OFFLINE= 'F';
	
	private String productId;
	private byte[] rawData;
	private Map<String, Object> data;
	private char deviceStatus;
	
	private String deviceKey;
	private String deviceSecret;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public byte[] getRawData() {
		return rawData;
	}
	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	public char getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(char deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceKey() {
		return deviceKey;
	}
	public void setDeviceKey(String deviceKey) {
		this.deviceKey = deviceKey;
	}
	public String getDeviceSecret() {
		return deviceSecret;
	}
	public void setDeviceSecret(String deviceSecret) {
		this.deviceSecret = deviceSecret;
	}
	
	
}
