package com.cetiti.ddapv2.process.service.support;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.service.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml", 
		"classpath:spring/httpClient.xml"})
public class DataServiceImplTest extends AbstractJUnit4SpringContextTests {
	
	@Resource
	private DataService dataService;
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testProcess() {
		Data data = new Data();
		data.setDeviceId("D1505467235604");
		Map<String, Object> map = new HashMap<>();
		map.put("temperature", 30);
		map.put("humidity", 80);
		data.setMapData(map);
		dataService.store(data);
	}

	@Test
	public void testStore() {
		Data data = new Data();
		data.setDeviceId("D1505467235604");
		Map<String, Object> map = new HashMap<>();
		map.put("temperature", 30);
		map.put("humidity", 80);
		data.setMapData(map);
		dataService.store(data);
	}

	@Test
	public void testAnalyze() {
		Data data = new Data();
		data.setDeviceId("D1505467235604");
		Map<String, Object> map = new HashMap<>();
		map.put("temperature", 50);
		map.put("humidity", 80);
		data.setMapData(map);
		dataService.analyze(data);
	}

	@Test
	public void testLatestData() {
		fail("Not yet implemented");
	}

	@Test
	public void testHistoryData() {
		fail("Not yet implemented");
	}

}
