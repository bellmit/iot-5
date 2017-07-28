package com.cetiti.ddapv2.process.model;
import java.util.Map;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月15日
 * 
 */
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
	
	@Override
	public String toString() {
		return "Product [protocol=" + protocol + ", attributes=" + attributes + ", attributeMap=" + attributeMap
				+ ", productKey=" + productKey + ", productSecret=" + productSecret + ", id=" + id + ", getName()="
				+ getName() + ", getDescription()=" + getDescription() + ", getOwner()=" + getOwner()
				+ ", getDataState()=" + getDataState() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
	
}
