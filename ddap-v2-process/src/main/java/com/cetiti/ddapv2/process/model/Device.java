package com.cetiti.ddapv2.process.model;

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
	private Product product;
	private Data data;
	private char deviceStatus;
	private double longitude;
	private double latitude;
	private String deviceKey;
	private String deviceSecret;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String toString() {
		return "Device [productId=" + productId + ", product=" + product + ", data=" + data + ", deviceStatus="
				+ deviceStatus + ", longitude=" + longitude + ", latitude=" + latitude + ", deviceKey=" + deviceKey
				+ ", deviceSecret=" + deviceSecret + ", toString()=" + super.toString() + "]";
	}
	
	
	
}
