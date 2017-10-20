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
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.service.DeviceService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import com.cetiti.ddapv2.process.web.DateConverter;
import com.cetiti.ddapv2.process.web.RestResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月21日
 * 
 */
@Api(value="/api/device", description="设备元数据查询接口")
@RestController
@RequestMapping("/api/device")
public class DeviceApi {
	
	@Resource
	private DeviceService deviceService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private DateConverter dateConverter;
	
	@ApiOperation("获取设备列表")
	@RequestMapping(value="/v1/list", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult getDevice(Device device, @ApiParam(value = "yyyy-MM-dd hh:mm:ss") String updateTime,
			ApiAssistParams assistParams, Page<Device> page){
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = new Account();
		account.setUserKey(assistParams.getKey());
		List<Account> accounts = accountService.getAccountList(account);
		if(null==accounts||accounts.size()<1){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		account = accounts.get(0);
		String descAttributes = device.getDescAttributes();
		device.setDescAttributes(null);
		Map<String, Object> map = new HashMap<>();
		map.putAll(device.toMap());
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
			device.setUpdateTime(dateConverter.convert(updateTime));
		}
		assistParams.setParams(map);
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		device.setOwner(account.getAccount());
		device.setDescAttributes(descAttributes);
		Page<Device> devices = deviceService.getDevicePage(account, device, page);
		List<Map<String, Object>> retn = new ArrayList<>();
		if(null!=devices&&null!=devices.getList()){
			retn = devices.getList().stream().map(Device::toMap).collect(Collectors.toList());
		}
		return RestResult.defaultSuccessResult(new Page<>(devices.getPageNum(),
				devices.getPageSize(), devices.getTotal(), retn));
	}

}
