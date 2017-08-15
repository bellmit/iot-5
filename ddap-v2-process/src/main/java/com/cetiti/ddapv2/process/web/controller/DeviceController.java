package com.cetiti.ddapv2.process.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.service.DeviceService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.web.RestResult;
import com.cetiti.ddapv2.process.web.RestSecurity;

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
	
	@Resource
	private DeviceService deviceService;
	
	@ApiOperation("新增设备")
	@RequestMapping("/new")
	public RestResult addDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.addDevice(account, device)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("修改设备")
	@RequestMapping("/update")
	public RestResult updateDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.updateDevice(account, device)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("删除设备")
	@RequestMapping("/delete")
	public RestResult deleteDevice(String deviceId, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.deleteDevice(account, deviceId)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("获取设备列表")
	@RequestMapping("/list")
	public RestResult getDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		return RestResult.defaultSuccessResult(deviceService.getDeviceList(account, device));
	}
}
