package com.cetiti.ddapv2.process.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.service.DeviceService;
import com.cetiti.ddapv2.process.util.DeviceFromExcel;
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
	@Resource
	private DeviceFromExcel deviceFromExcel;
	
	@ApiOperation("新增设备")
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public RestResult addDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.addDevice(account, device)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("从excel导入设备")
	@RequestMapping(value="/importfromexcel", method=RequestMethod.POST)
	public RestResult importDeviceFromExcel(MultipartFile excel, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(null!=excel&&deviceService.importDevices(account, deviceFromExcel.readDeviceList(excel))){
			return RestResult.defaultSuccessResult(excel.getName());
		}
		return RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("修改设备")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestResult updateDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.updateDevice(account, device)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("删除设备")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public RestResult deleteDevice(String deviceId, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(deviceService.deleteDevice(account, deviceId)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("获取设备列表")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public RestResult getDevice(Device device, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		return RestResult.defaultSuccessResult(deviceService.getDeviceList(account, device));
	}
}
