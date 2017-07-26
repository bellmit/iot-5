package com.cetiti.ddapv2.process.web.controller;

import java.util.Date;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Thing;
import com.cetiti.ddapv2.process.model.Trigger;
import com.cetiti.ddapv2.process.web.RestResult;

import io.swagger.annotations.ApiOperation;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
@RequestMapping("/device")
@RestController
public class DeviceController {
	
	@ApiOperation("测试接口")
	@RequestMapping("/thing")
	public RestResult thing()
	{
		Thing thing = new Thing();
		thing.setId("id002");
		thing.setName("newThing");
		return RestResult.defaultSuccessResult(thing);
	}
	
	@RequestMapping("/trigger")
	public RestResult trigger()
	{
		Trigger trigger = new Trigger();
		trigger.setDate(new Date());
		trigger.setName("trigger");
		
		return RestResult.defaultSuccessResult(trigger);
	}

}
