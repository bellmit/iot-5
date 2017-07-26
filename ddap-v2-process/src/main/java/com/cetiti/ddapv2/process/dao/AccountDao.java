package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Account;

public interface AccountDao {
	
	int insertUser(Account account);
	
	int deleteUser(String accountName);
	
	int updateUser(Account account);
	
	List<Account> selectUserList();

}
