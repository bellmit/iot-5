package com.cetiti.ddapv2.process.model;
import java.util.List;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月15日
 * 
 */
public class Product extends Thing {
	
	private String protocol;
	private String dataAttributes;
	//标签和单位 考虑加一个label
	private List<DataItem> dataAttributeList;

	private String productKey;
	private String productSecret;
	
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	public String getDataAttributes() {
		return dataAttributes;
	}
	public void setDataAttributes(String dataAttributes) {
		this.dataAttributes = dataAttributes;
	}
	public List<DataItem> getDataAttributeList() {
		return dataAttributeList;
	}
	public void setDataAttributeList(List<DataItem> dataAttributeList) {
		this.dataAttributeList = dataAttributeList;
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
		return "Product [protocol=" + protocol + ", dataAttributes=" + dataAttributes + ", dataAttributeList="
				+ dataAttributeList + ", productKey=" + productKey + ", productSecret=" + productSecret + ", id=" + id
				+ ", getName()=" + getName() + ", getDescription()=" + getDescription() + ", getOwner()=" + getOwner()
				+ ", getDescAttributes()=" + getDescAttributes() + ", getDescAttributeList()=" + getDescAttributeList()
				+ ", getDataState()=" + getDataState() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()="
				+ getUpdateTime() + "]";
	}
	
}
