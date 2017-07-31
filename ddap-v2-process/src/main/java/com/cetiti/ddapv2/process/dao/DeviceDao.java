package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Device;

public interface DeviceDao {
	
	int insertDevice(Device device);
	
	int deleteDevice(String deviceId);
	
	int updateDevice(Device device);
	
	Device selectDevice(String deviceId);
	
	List<Device> selectDeviceList();

}
