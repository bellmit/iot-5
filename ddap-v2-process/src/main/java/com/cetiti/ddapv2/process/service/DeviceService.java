package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Product;
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
	
	int addDevice(Account user, Device device);
	
	int updateDevice(Account user, Device device);
	
	int deleteDevice(Account user, Device device);
	
	List<Device> getUserDevice(Account user, Product type);
	
	int controlDevice(Account user, String cmd);
}
