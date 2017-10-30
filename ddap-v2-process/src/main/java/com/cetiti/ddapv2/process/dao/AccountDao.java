package com.cetiti.ddapv2.process.dao;

import java.util.List;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;



public interface AccountDao {
	
	int insertAccount(Account account);
	
	int deleteAccount(String accountName);
	
	int updateAccount(Account account);
	
	List<Account> selectAccountList();
	
	Account selectAccount(String account);
	
	List<Account> selectAccountList(Account account);
	
	List<Account> selectAccountList(Account account, Page<?> page);
	
	int countAccount(Account account);

}
