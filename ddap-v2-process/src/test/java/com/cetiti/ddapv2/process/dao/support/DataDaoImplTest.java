package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.dao.DataDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml"})
public class DataDaoImplTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private DataDao dataDao;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInsertData() {
		dataDao.insertData("12313200", "testst");
	}

	@Test
	public void testSelectData() {
		//dataDao.selectData("12313200", 100).stream().forEach(System.out::println);
	}

}
