package com.cetiti.ddapv2.process.web.api;

import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/acceptor")
public class HttpAcceptorApi {
	
	@ApiOperation("模拟数据上报")
	@RequestMapping(value="/http/{product}", method=RequestMethod.POST)
	public String process(@PathVariable("product") String product, @RequestBody String data){
		//HttpMessageConverters converters = new HttpMessageConverters(additionalConverters);
		System.out.println("product: "+product);
		System.out.println("data: "+data);
		if("easylink".equals(product)){
			
		}
		return "ok";
	}
}
