package com.cetiti.ddapv2.process.service;

import java.util.List;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;



/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public interface AccountService {
	
	Account getAccount(String getWho, Account whoGet);
	
	Account getAccountByKey(String uKey);
	
	Account addAccount(Account account);
	
	boolean deleteAccount(String deleteWho, Account whoDelete);
	
	boolean updateAccount(Account updateWho, Account whoUpdate);
	
	Account login(Account account);
	
	List<Account> getAccountList(Account selectWho, Account whoSelect);
	
	Page<Account> getAccountPage(Account selectWho, Page<Account> page, Account whoSelect);
	
	boolean resetPassword(String resetWho, Account whoReset);
	
	boolean changePassword(String newPassword, Account who);
}
