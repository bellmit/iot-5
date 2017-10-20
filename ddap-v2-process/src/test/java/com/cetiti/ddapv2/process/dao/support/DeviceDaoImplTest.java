package com.cetiti.ddapv2.process.dao.support;

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Item;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.JsonUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml","classpath:spring/httpClient.xml"})
public class DeviceDaoImplTest extends AbstractJUnit4SpringContextTests{
	
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private JsonUtil jsonUtil;
	
	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInsertDevice() {
		Device device = new Device();
		device.setSerialNumber("004a770124000725");
		device.setName("sensor2");
		device.setDescription("ddap sensor");
		List<Item> itemlist = new ArrayList<>();
		Item item1 = new Item("部门", "department", "开发部");
		Item item2 = new Item("维修单位", "repair", "意博");
		itemlist.add(item1);
		itemlist.add(item2);
		device.setDescAttributes(jsonUtil.toJson(itemlist));
		
		device.setProductId("P1501488322947");
		device.setDeviceStatus(Device.STATUS_ONLINE);
		device.setLongitude(120.123456789);
		device.setLatitude(30.123456789);
		device.setDeviceKey(EncryptUtil.generateDeviceKey(device));
		device.setDeviceSecret(EncryptUtil.generateDeviceSecret(device));
		device.setOwner("admin");
		deviceDao.insertDevice(device);
	}

	@Ignore
	public void testDeleteDevice() {
		deviceDao.deleteDevice("D1501489788148");
	}

	@Ignore
	public void testUpdateDevice() {
		Device device = new Device();
		device.setId("D1505467027745");
		device.setSerialNumber("004a770124000726");
		device.setName("sensor2u");
		device.setDescription("ddap sensoru");
		List<Item> itemlist = new ArrayList<>();
		Item item1 = new Item("部门", "department", "开发部u");
		Item item2 = new Item("维修单位", "repair", "意博u");
		itemlist.add(item1);
		itemlist.add(item2);
		device.setDescAttributes(jsonUtil.toJson(itemlist));
		
		device.setProductId("P1501488322947");
		device.setDeviceStatus(Device.STATUS_OFFLINE);
		device.setDeviceKey(EncryptUtil.generateDeviceKey(device));
		device.setDeviceSecret(EncryptUtil.generateDeviceSecret(device));
		device.setOwner("adminu");
		device.setLongitude(119.87451236);
		device.setLatitude(29.985461237);
		deviceDao.updateDevice(device);
	}

	@Ignore
	public void testSelectDeviceList() {
		System.out.println(deviceDao.selectDevice("D1505467027745"));
	}

	@Ignore
	public void testSelectDevice() {
		Device device = new Device();
		device.setSerialNumber("004a770124000725");
		device.setId("D1505467027745");
		device.setName("sensor2u");
		device.setDescription("ddap sensoru");
		device.setDescAttributes("lifetime");
		device.setProductId("P1501488322947");
		device.setDeviceStatus(Device.STATUS_OFFLINE);
		device.setOwner("adminu");
		
		deviceDao.selectDeviceList(device).stream().forEach(System.out::println);
	}
	
	@Test
	public void testSelectDevicePage() throws Exception {
		
		Device device = new Device();
		/*device.setSerialNumber("004a770124000725");
		device.setId("D1505467027745");
		device.setName("sensor2u");
		device.setDescription("ddap sensoru");
		device.setDescAttributes("lifetime");
		device.setProductId("P1501488322947");
		device.setDeviceStatus(Device.STATUS_OFFLINE);
		device.setOwner("adminu");*/
		Page<?> page = new Page<>();
		page.setPageNum(1);
		page.setPageSize(4);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		device.setUpdateTime(dateFormat.parse("2017-10-01 00:00:00"));
		
		deviceDao.selectDeviceList(device, page).stream().forEach(System.out::println);
	}
	
	@Test
	public void testCountDevice() throws Exception {
		Device device = new Device();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		device.setUpdateTime(dateFormat.parse("2017-10-01 00:00:00"));
		
		int count = deviceDao.countDevice(device);
		System.out.println(count);
	}

}
