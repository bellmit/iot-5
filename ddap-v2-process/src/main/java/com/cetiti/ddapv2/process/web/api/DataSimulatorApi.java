package com.cetiti.ddapv2.process.web.api;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.service.DataService;
import com.cetiti.ddapv2.process.util.JsonUtil;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.web.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value="/api/data", description="模拟感知数据上报和告警数据接收")
@RestController
@RequestMapping("/api/simulator")
public class DataSimulatorApi {
	
	private static final String DEVICE_ID = "deviceId";
	
	@Resource
	private DataService datasService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private JsonUtil jsonUtil;
	
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "deviceId", dataType = "string", required = true, value = "设备id"),
			@ApiImplicitParam(name = "anyAttr", dataType = "string", required = true, value = "设备感知数据， 如 temperature：30")
	})
	@ApiOperation("模拟数据上报")
	@RequestMapping(value="/v1/upload", method=RequestMethod.POST)
	public RestResult uploadData(@RequestBody String data){
		System.out.println("data: "+ data);
		if(!StringUtils.hasText(data)) {
			return RestResult.defaultFailResult("data null");
		}
		Map<String, Object> mapData = jsonUtil.mapFromJson(data, String.class, Object.class);
		if(null==mapData){
			return RestResult.defaultFailResult(data + "not json data");
		}
		if(null==mapData.get(DEVICE_ID)){
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", DEVICE_ID));
		}
		Data objData = new Data();
		objData.setDeviceId(mapData.get(DEVICE_ID).toString());
		mapData.remove(DEVICE_ID);
		objData.setMapData(mapData);
		objData.setTimeStamp((int)(System.currentTimeMillis()/1000));
		
		datasService.process(objData);
		
		return RestResult.defaultSuccessResult(data);
	}
	
	@ApiOperation("模拟告警数据接收")
	@RequestMapping(value="/v1/warndata", method=RequestMethod.POST)
	public RestResult warnData(@RequestBody String data){
		
		System.out.println("warndata: "+ data);
		
		return RestResult.defaultSuccessResult(data);
	}


}
