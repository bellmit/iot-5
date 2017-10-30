package com.cetiti.ddapv2.process.web.api;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.service.DataService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import com.cetiti.ddapv2.process.web.DateConverter;
import com.cetiti.ddapv2.process.web.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value="/api/data", description="设备感知数据查询接口")
@RestController
@RequestMapping("/api/data")
public class DataApi {
	
	@Resource
	private DataService datasService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private DateConverter dateConverter;
	
	@ApiOperation("根据deviceId获取最新数据")
	@RequestMapping(value="/v1/latest", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult getLatestData(String deviceId, ApiAssistParams assistParams){
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.hasText(deviceId)){
			map.put("deviceId", deviceId);
		}
		assistParams.setParams(map);
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		Map<String, Object> data = datasService.latestData(deviceId, account);
		
		return null==data?RestResult.defaultFailResult(MessageContext.getMsg()):
			RestResult.defaultSuccessResult(data);
	}
	
	@ApiOperation("根据deviceId分页获取历史数据, ")
	@RequestMapping(value="/v1/history", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult getHistoryData(String deviceId, ApiAssistParams assistParams, 
			Page<Map<String, Object>> page, @ApiParam(value = "yyyy-MM-dd hh:mm:ss") String startTime, 
			@ApiParam(value = "yyyy-MM-dd hh:mm:ss") String endTime){
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.hasText(deviceId)){
			map.put("deviceId", deviceId);
		}
		if(page.getPageNum()>0){
			map.put("pageNum", page.getPageNum());
		}
		if(page.getPageSize()>0){
			map.put("pageSize", page.getPageSize());
		}
		if(StringUtils.hasText(startTime)){
			map.put("startTime", startTime);
		}
		if(StringUtils.hasText(endTime)){
			map.put("endTime", endTime);
		}
		assistParams.setParams(map);
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		Page<Map<String, Object>> datas = datasService.historyData(deviceId,
				dateConverter.convert(startTime), dateConverter.convert(endTime), page, account);
		return null==datas?RestResult.defaultFailResult(MessageContext.getMsg()):
			RestResult.defaultSuccessResult(datas);
	}

}
