package com.cetiti.ddapv2.process.service.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.web.ApiAssistParams;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月1日
 * 
 */
@Service
public class AccountServiceImpl implements AccountService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);
	
	@Resource
	private AccountDao accountDao;
	@Resource
	private MessageUtil msgUtil;
	
	@Override
	public Account getAccount(String getWho, Account whoGet){
		if(null==getWho||null==whoGet){
			return null;
		}
		if(!whoGet.isAdmin()&&!getWho.equals(whoGet.getAccount())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return null;
		}
		return getAccount(getWho);
	}
	
	private Account getAccount(String account) {
		try{
			return accountDao.selectAccount(account);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getAccount [{}] exception [{}]", account, e.getMessage());
		}
		return null;
	}
	
	@Override
	public Account getAccountByKey(String uKey) {
		if(!StringUtils.hasText(uKey)){
			return null;
		}
		Account account = new Account();
		account.setUserKey(uKey);
		try{
			List<Account> accounts = accountDao.selectAccountList(account);
			if(null!=accounts&&accounts.size()>0){
				return accounts.get(0);
			}
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getAccountByKey [{}] exception [{}]", uKey, e.getMessage());
		}
		return null;
	}

	@Override
	public Account addAccount(Account account) {
		if(null==account){
			return null;
		}
		if(!StringUtils.hasText(account.getAccount())){
			MessageContext.setMsg(msgUtil.get("account.null"));
			return null;
		}
		if(!StringUtils.hasText(account.getPassword())){
			MessageContext.setMsg(msgUtil.get("password.null"));
			return null;
		}
		if(null!=getAccount(account.getAccount())){
			MessageContext.setMsg(msgUtil.get("account.exist"));
			return null;
		}
		//account.setRole(Account.ROLE_PLATFORM);
		account.setUserKey(EncryptUtil.generateUserKey(account));
		account.setUserSercret(EncryptUtil.generateUserSecret(account));
		try{
			accountDao.insertAccount(account);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("addAccount [{}] exception [{}]", account, e.getMessage());
			return null;
		}
		MessageContext.setMsg(msgUtil.get("regist.success"));
		return account;
	}

	@Override
	public boolean deleteAccount(String deleteWho, Account whoDelete) {
		if(!StringUtils.hasText(deleteWho)){
			return true;
		}
		if(null==whoDelete||!whoDelete.isAdmin()){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			accountDao.deleteAccount(deleteWho);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] deleteAccount [{}] exception [{}]", 
					whoDelete.getAccount(), deleteWho, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateAccount(Account updateWho, Account whoUpdate) {
		if(null==updateWho||!StringUtils.hasText(updateWho.getAccount())){
			return true;
		}
		if(null==whoUpdate||!whoUpdate.isAdmin()&&!updateWho.getAccount().equals(whoUpdate.getAccount())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		if(!whoUpdate.isAdmin()&&updateWho.isAdmin()){
			updateWho.setRole(null);
		}
		try{
			accountDao.updateAccount(updateWho);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] updateAccount [{}] exception [{}]", whoUpdate.getAccount(), updateWho, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public Account login(Account account) {
		if(null==account||!StringUtils.hasText(account.getAccount())
				||!StringUtils.hasText(account.getPassword())){
			MessageContext.setMsg(msgUtil.get("password.incorrect"));
			return null;
		}
		long timestamp = System.currentTimeMillis();
		if(null!=account.getToken()){
			try{
				timestamp = Long.parseLong(account.getToken());
			}catch (NumberFormatException e) {
				LOGGER.error("parseLong [{}] exception [{}]", account.getToken(), e.getMessage());
			}
		}
		if(ApiAssistParams.isRequestExpired(account.getAccount()+timestamp, timestamp)){
			MessageContext.setMsg(msgUtil.get("request.expired"));
			return null;
		}
		Account dbaccount = getAccount(account.getAccount());
		if(null==dbaccount){
			MessageContext.setMsg(msgUtil.get("password.incorrect"));
			return null;
		}
		if(!account.getPassword().equals(EncryptUtil.md5(dbaccount.getPassword()+account.getToken()))){
			MessageContext.setMsg(msgUtil.get("password.incorrect"));
			return null;
		}
		ApiAssistParams.registRequest(account.getAccount()+timestamp, timestamp);
		return dbaccount;
	}

	@Override
	public List<Account> getAccountList(Account selectWho, Account whoSelect) {
		if(null==selectWho||null==whoSelect){
			return null;
		}
		if(!whoSelect.isAdmin()){
			selectWho.setAccount(whoSelect.getAccount());
		}
		try{
			return accountDao.selectAccountList(selectWho);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getAccountList [{}]", e.getMessage());
		}
		return null;
	}

	@Override
	public Page<Account> getAccountPage(Account selectWho, Page<Account> page, Account whoSelect) {
		if(null==selectWho||null==whoSelect){
			return null;
		}
		if(!whoSelect.isAdmin()){
			selectWho.setAccount(whoSelect.getAccount());
		}
		CompletableFuture<List<Account>> accountFuture = new CompletableFuture<>();
		new Thread(() -> {
			List<Account> list = new ArrayList<>();
			try{
				list = accountDao.selectAccountList(selectWho, page);
				accountFuture.complete(list);
			}catch (Exception e) {
				MessageContext.setMsg(msgUtil.get("db.exception"));
				LOGGER.error("selectAccountList [{}] page[{}] exception [{}]", selectWho, page, e.getMessage());
				accountFuture.completeExceptionally(e);
			}
		}).start();
		int count = 0;
		try{
			count = accountDao.countAccount(selectWho);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("countAccount [{}] exception [{}]", selectWho, e.getMessage());
		}
		page.setTotal(count);
		try {
			page.setList(accountFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("page.setList [{}]", e.getMessage());
			return null;
		}
		
		return page;
	}

	@Override
	public boolean resetPassword(String resetWho, Account whoReset) {
		if(!StringUtils.hasText(resetWho)){
			return true;
		}
		if(null==whoReset||!whoReset.isAdmin()){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		Account account = new Account();
		account.setAccount(resetWho);
		account.setPassword(EncryptUtil.md5(resetWho+Account.PASSWORD_DEFAULT));
		return updateAccount(account, whoReset);
	}

	@Override
	public boolean changePassword(String newPassword, Account who) {
		if(null==who||!StringUtils.hasText(who.getAccount())){
			return false;
		}
		Account dbAccount = getAccount(who.getAccount());
		if(null==who.getPassword()||null==dbAccount||!who.getPassword().equals(dbAccount.getPassword())){
			MessageContext.setMsg(msgUtil.get("password.incorrect"));
			return false;
		}
		Account account = new Account();
		account.setAccount(who.getAccount());
		account.setPassword(newPassword);
		return updateAccount(account, who);
	}

}
