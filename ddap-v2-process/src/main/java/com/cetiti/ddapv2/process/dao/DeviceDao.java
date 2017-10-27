package com.cetiti.ddapv2.process.dao;

import java.util.Date;
import java.util.List;

import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Page;

public interface DeviceDao {
	
	int insertDevice(Device device);
	
	int insertDevices(List<Device> deviceList);
	
	int deleteDevice(String deviceId);
	
	int deleteOldDevice(String productId, Date updateTime);
	
	int updateDevice(Device device);
	
	int updateDeviceBySerialNumberAndProductId(Device device);
	
	Device selectDevice(String deviceId);
	
	List<Device> selectDeviceList(Device device);
	
	List<Device> selectDeviceList(Device device, Page<?> page);
	
	int countDevice(Device device);

}
