package com.cetiti.ddapv2.process.service;

import com.cetiti.ddapv2.process.model.Account;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface AccountService {
	
	Account getAccount(String account);
	
	int addAccount(Account account);
	
	int deleteAccount(String account);
	
	int updateAccount(Account account);
	
	boolean login(String account, String password);
	
	boolean logout();
}
