package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.util.EncryptUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml"})
public class AccountDaoImplTest {
	
	@Resource
	private AccountDao accountDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInstetAccount() {
		Account account = new Account();
		account.setAccount("admin");
		account.setPassword("admin123456");
		account.setRole(Account.ROLE_ADMIN);
		account.setPhone("88366841");
		account.setAddress("海创园");
		account.setEmail("admin@cetiti.com");
		account.setUserKey(EncryptUtil.generateUserKey(account.getAccount()));
		account.setUserSercret(EncryptUtil.generateUserSecret(account.getAccount()));
		accountDao.insertAccount(account);
	}
	
	@Ignore
	public void testUpdateAccount() {
		Account account = new Account();
		account.setAccount("admin");
		account.setPassword(EncryptUtil.md5("admin123456"));
		/*account.setRole(Account.ROLE_PLATFORM);
		account.setPhone("88366841");
		account.setAddress("海创园");
		account.setEmail("account@cetiti.com");
		account.setUserKey(EncryptUtil.generateUserKey(account.getAccount()));
		account.setUserSercret(EncryptUtil.generateUserSecret(account.getAccount()));*/
		accountDao.updateAccount(account);
	}
	
	@Test
	public void testSelectAccount() {
		System.out.println(accountDao.selectAccount("account"));
	}
	
	@Ignore
	public void testSelectAccountList(){
		accountDao.selectAccountList().stream().forEach(System.out::println);
	}
	

}
