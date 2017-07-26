package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.model.Account;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface DeviceTypeService {
	
	int addDeviceType(Account user, Product type);
	
	int deleteDeviceType(Account user, Product type);
	
	int updateDeviceType(Account user, Product type);
	
	List<Product> getUserDeviceType(Account user);
	
}
