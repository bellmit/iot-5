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

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.util.EncryptUtil;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/acceptors.xml", "classpath:spring/store.xml"})
public class DeviceDaoImplTest extends AbstractJUnit4SpringContextTests{
	
	@Resource
	private DeviceDao deviceDao;

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testInsertDevice() {
		Device device = new Device();
		device.setName("sensor2");
		device.setDescription("ddap sensor");
		device.setDescAttributes("org, admin");
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
		device.setId("D1501489856635");
		device.setName("sensor2u");
		device.setDescription("ddap sensoru");
		device.setDescAttributes("lifetime");
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
		System.out.println(deviceDao.selectDevice("D1502711779838"));
	}

	@Test
	public void testSelectDevice() {
		Device device = new Device();
		device.setId("D1501489856635");
		device.setName("sensor2u");
		device.setDescription("ddap sensoru");
		device.setDescAttributes("lifetime");
		device.setProductId("P1501488322947");
		device.setDeviceStatus(Device.STATUS_OFFLINE);
		device.setOwner("adminu");
		
		deviceDao.selectDeviceList(device).stream().forEach(System.out::println);
	}

}
