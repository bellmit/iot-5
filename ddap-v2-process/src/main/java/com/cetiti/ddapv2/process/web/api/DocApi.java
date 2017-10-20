package com.cetiti.ddapv2.process.web.api;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import io.swagger.annotations.ApiOperation;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年10月11日
 * https://github.com/swagger-api/swagger-core/wiki/Annotations#apiparam
 */
@RestController
@RequestMapping("/api/doc")
public class DocApi {
	
	@ApiOperation("接口文档")
	@RequestMapping(value="/v1", method=RequestMethod.HEAD)
	public void paramDoc(@RequestBody RuleExpression rule, @RequestBody ApiAssistParams assistParams,
			@RequestBody Page<RuleExpression> pagerule, @RequestBody Page<Map<String, Object>> pagedata,
			@RequestBody Page<Device> pagedevice, @RequestBody Page<Product> pageproduct){
	}

}
