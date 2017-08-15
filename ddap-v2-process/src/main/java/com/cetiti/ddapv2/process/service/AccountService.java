package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Account;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface AccountService {
	
	Account getAccount(String account);
	
	Account addAccount(Account account);
	
	boolean deleteAccount(String account);
	
	boolean updateAccount(Account account);
	
	Account login(Account account);
	
	List<Account> getAccountList(Account account);
}
