package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Account;

public interface AccountDao {
	
	int insertAccount(Account account);
	
	int deleteAccount(String accountName);
	
	int updateAccount(Account account);
	
	List<Account> selectAccountList();
	
	Account selectAccount(String account);

}
