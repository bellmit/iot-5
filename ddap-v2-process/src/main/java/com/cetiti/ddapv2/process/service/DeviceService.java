package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Account;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface DeviceService {
	
	public static final String COMMAND_ON = "1";
	public static final String COMMAND_OFF = "0";
	
	boolean addDevice(Account account, Device device);
	
	boolean updateDevice(Account account, Device device);
	
	boolean deleteDevice(Account account, String deviceId);
	
	List<Device> getDeviceList(Account account, Device device);
	
	int cmd(Account account, Device device, String cmd);
}
