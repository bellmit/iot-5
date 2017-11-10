package com.cetiti.ddapv2.process.web.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.service.ProductService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import com.cetiti.ddapv2.process.web.DateConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月21日
 * 
 */
@Api(value="/api/product", description="产品(设备类型)查询接口")
@RestController
@RequestMapping("/api/product")
public class ProductApi {
	
	@Resource
	private ProductService productService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private DateConverter dateConverter;
	
	@ApiOperation("获取产品列表")
	@RequestMapping(value="/v1/list", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult getProduct(Product product, @ApiParam(value = "yyyy-MM-dd hh:mm:ss") String updateTime, 
			ApiAssistParams assistParams, Page<Product> page) {
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		String descAttributes = product.getDescAttributes();
		product.setDescAttributes(null);
		Map<String, Object> map = new HashMap<>();
		map.putAll(product.toMap());
		if(StringUtils.hasText(descAttributes)){
			map.put("descAttributes", descAttributes);
		}
		if(page.getPageNum()>0){
			map.put("pageNum", page.getPageNum());
		}
		if(page.getPageSize()>0){
			map.put("pageSize", page.getPageSize());
		}
		if(StringUtils.hasText(updateTime)){
			map.put("updateTime", updateTime);
			product.setUpdateTime(dateConverter.convert(updateTime));
		}
		assistParams.setParams(map);
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		product.setOwner(account.getAccount());
		product.setDescAttributes(descAttributes);
		Page<Product> products = productService.getProductPage(account, product, page);
		List<Map<String, Object>> retn = new ArrayList<>();
		if(null!=products&&null!=products.getList()){
			retn = products.getList().stream().map(Product::toMap).collect(Collectors.toList());
		}
		
		return RestResult.defaultSuccessResult(new Page<>(products.getPageNum(),
				products.getPageSize(), products.getTotal(), retn));
	}

}
