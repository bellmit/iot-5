package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Device;

public interface DeviceDao {
	
	int insertDevice(Device device);
	
	int deleteDevice(Device device);
	
	int updateDevice(Device device);
	
	List<Device> selectDeviceList();

}
