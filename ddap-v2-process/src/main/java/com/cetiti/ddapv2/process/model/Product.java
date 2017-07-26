package com.cetiti.ddapv2.process.model;
import java.util.Map;

public class Product extends Thing {
	
	private String protocol;
	private String attributes;
	//标签和单位
	private Map<String, String> attributeMap; 
	
	private String productKey;
	private String productSecret;
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}
	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
	public String getProductKey() {
		return productKey;
	}
	public void setProductKey(String productKey) {
		this.productKey = productKey;
	}
	public String getProductSecret() {
		return productSecret;
	}
	public void setProductSecret(String productSecret) {
		this.productSecret = productSecret;
	}
	
}