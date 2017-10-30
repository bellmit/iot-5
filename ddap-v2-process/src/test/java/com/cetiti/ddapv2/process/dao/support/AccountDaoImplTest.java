package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.util.EncryptUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:spring/*.xml")
public class AccountDaoImplTest {
	
	@Resource
	private AccountDao accountDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testInstetAccount() {
		Account account = new Account();
		account.setAccount("test");
		account.setName("testName");
		account.setDataPostUrl("http://www.post.com");
		account.setPassword("123456");
		account.setRole(Account.ROLE_PLATFORM);
		account.setPhone("88366841");
		account.setAddress("海创园");
		account.setEmail("admin@cetiti.com");
		account.setUserKey(EncryptUtil.generateUserKey(account.getAccount()));
		account.setUserSercret(EncryptUtil.generateUserSecret(account.getAccount()));
		account.setPassword(EncryptUtil.md5(account.getAccount()+account.getPassword()));
		accountDao.insertAccount(account);
	}
	
	@Test
	public void testUpdateAccount() {
		Account account = new Account();
		account.setAccount("adminu");
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
	
	@Test
	public void testSelectAccountList(){
		accountDao.selectAccountList(null).stream().forEach(System.out::println);
	}
	

}
