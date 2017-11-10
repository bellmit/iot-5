package com.cetiti.ddapv2.process.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.service.ProductService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.web.RestSecurity;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Resource
	private ProductService productService;
	
	@ApiOperation("新建产品")
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public RestResult addProduct(Product product, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(productService.addProduct(account, product)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("修改产品")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestResult updateProduct(Product product, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(productService.updateProduct(account, product)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("删除产品")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public RestResult deleteProduct(String productId, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(productService.deleteProduct(account, productId)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("获取产品列表")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public RestResult getProduct(Product product, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		return RestResult.defaultSuccessResult(productService.getProductList(account, product));
	}

}
