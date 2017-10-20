package com.cetiti.ddapv2.process.model;

import java.util.Map;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class RuleExpression extends Thing {
	
	private String productId;
	private String deviceId;
	@ApiModelProperty("boolean expression like: temperature<=44&&humidity>56&&humidity<90 ")
	private String expression;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	
	@Override
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if(StringUtils.hasText(this.productId)){
			map.put("productId", this.productId);
		}
		if(StringUtils.hasText(this.deviceId)){
			map.put("deviceId", this.deviceId);
		}
		if(StringUtils.hasText(this.expression)){
			map.put("expression", this.expression);
		}
		
		return map;
	}

	@Override
	public String toString() {
		return "RuleExpression [productId=" + productId + ", deviceId=" + deviceId + ", expression=" + expression
				+ ", id=" + id + ", toString()=" + super.toString() + "]";
	}

}
