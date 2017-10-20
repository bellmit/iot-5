package com.cetiti.ddapv2.process.model;

import java.util.Map;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModelProperty;

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
	private String serialNumber;
	@ApiModelProperty(hidden=true)
	private Product product;
	@ApiModelProperty(hidden=true)
	private Data data;
	private char deviceStatus;
	private double longitude;
	private double latitude;
	@ApiModelProperty(hidden=true)
	private String deviceKey;
	@ApiModelProperty(hidden=true)
	private String deviceSecret;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
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
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if(StringUtils.hasText(this.productId)){
			map.put("productId", this.productId);
		}
		if(StringUtils.hasText(this.serialNumber)){
			map.put("serialNumber", this.serialNumber);
		}
		if(STATUS_ONLINE==this.deviceStatus||STATUS_OFFLINE==this.deviceStatus){
			map.put("deviceStatus", this.deviceStatus);
		}
		if(this.longitude>0){
			map.put("longitude", this.longitude);
		}
		if(this.latitude>0){
			map.put("latitude", this.latitude);
		}
		if(StringUtils.hasText(this.deviceKey)){
			map.put("deviceKey", this.deviceKey);
		}
		if(StringUtils.hasText(this.deviceSecret)){
			map.put("deviceSecret", this.deviceSecret);
		}
		return map;
	}
	
	@Override
	public String toString() {
		return "Device [productId=" + productId + ", serialNumber=" + serialNumber + ", product=" + product + ", data="
				+ data + ", deviceStatus=" + deviceStatus + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", deviceKey=" + deviceKey + ", deviceSecret=" + deviceSecret + ", id=" + id + ", toString()="
				+ super.toString() + "]";
	}
	
}
