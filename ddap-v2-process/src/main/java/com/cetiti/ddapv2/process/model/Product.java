package com.cetiti.ddapv2.process.model;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月15日
 * 
 */
public class Product extends Thing {
	
	private String protocol;
	@ApiModelProperty(hidden=true)
	private String dataAttributes;
	//标签和单位 考虑加一个label
	@ApiModelProperty(hidden=true)
	private List<DataItem> dataAttributeList;
	@ApiModelProperty(hidden=true)
	private String productKey;
	@ApiModelProperty(hidden=true)
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if(StringUtils.hasText(this.protocol)){
			map.put("protocol", this.protocol);
		}
		if(StringUtils.hasText(this.dataAttributes)){
			if(null==dataAttributeList){
				JsonUtil jsonUtil = new JsonUtil();
				dataAttributeList = jsonUtil.listFromJson(this.dataAttributes, DataItem.class);
			}
			for(DataItem item:dataAttributeList){
				map.put(item.getKey(), item.getLabel());
				map.put(item.getKey()+"Unit", item.getUnit());
			}
		}
		if(StringUtils.hasText(this.productKey)){
			map.put("productKey", this.productKey);
		}
		if(StringUtils.hasText(this.productSecret)){
			map.put("productSecret", productSecret);
		}
		
		return map;
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
