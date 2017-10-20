package com.cetiti.ddapv2.process.dao;

import java.util.Date;
import java.util.List;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Page;

public interface DataDao {
	
	int insertData(String deviceId, String data);
	
	int insertData(String deviceId, String data, Date timpstamp);
	
	List<Data> selectData(String deviceId, Date startTime, Date endTime, Page<?> page);
	
	int countData(String deviceId, Date startTime, Date endTime);
	
	Data selectLatestData(String deviceId);
	
}
