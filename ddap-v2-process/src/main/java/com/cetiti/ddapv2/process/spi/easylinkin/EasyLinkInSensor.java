package com.cetiti.ddapv2.process.spi.easylinkin;

import javax.annotation.Resource;

import com.cetiti.ddapv2.process.spi.Codec;
import com.cetiti.ddapv2.process.util.JsonUtil;

public abstract class EasyLinkInSensor implements Codec<LoraData, String>{
	
	private String productId;
	@Resource
	private JsonUtil jsonUtil;
	
	@Override
	public boolean isMatch(String productId) {
		return null!=this.productId&&this.productId.equals(productId);
	}
	
	@Override
	public String encode(LoraData payload) {
		return jsonUtil.toJson(new Response());
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
}
