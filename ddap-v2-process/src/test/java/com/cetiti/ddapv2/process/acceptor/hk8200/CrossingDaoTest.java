package com.cetiti.ddapv2.process.acceptor.hk8200;

import static org.junit.Assert.*;

import java.util.Date;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Page;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年10月20日
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-two-datasources
 * https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration("classpath:spring/*.xml")
public class CrossingDaoTest {
	@Resource
	private CrossingDao crossingDao;

	@Before
	public void setUp() throws Exception {
		//BeanPropertyRowMapper<T>
	}

	@Ignore
	public void testSelectCrossingList() {
		System.out.println(crossingDao.selectCrossingList(new Date(), new Page<>(1, 20)));
	}
	
	@Ignore
	public void testCountCrossing() {
		System.out.println(crossingDao.countCrossing(new Date()));
	}
	
	@Ignore
	public void testSelectCrossing() {
		System.out.println(crossingDao.selectCrossing("7"));
	}
	
	@Test
	public void testFullUpdate() {
		crossingDao.fullUpdate();
	}
	
	@Ignore
	public void testSelectComments() {
		System.out.println(crossingDao.selectColumnComments("BMS_CROSSING_INFO"));
	}

}
