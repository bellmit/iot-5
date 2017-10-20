package com.cetiti.ddapv2.process.service;

import java.util.Date;
import java.util.Map;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Page;

public interface DataService {
	
	void process(Data data);
	
    void store(Data data);
	
	void analyze(Data data);
	
	Map<String, Object> latestData(String deviceId, Account account);
	
	Page<Map<String, Object>> historyData(String deviceId, Date beginTime, 
			Date endTime, Page<Map<String, Object>> page, Account account);

}
