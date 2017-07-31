package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Data;

public interface DataDao {
	
	int insertData(String deviceId, String data);
	
	List<Data> selectData(String deviceId, int timestamp);
	
}
