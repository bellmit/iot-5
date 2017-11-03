package com.cetiti.ddapv2.process.util;

import static org.junit.Assert.*;

import java.io.File;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;

public class DeviceFromExcelTest {
	
	@Resource
	private DeviceFromExcel deviceFromExcel;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testReadDeviceList() {
		File file = new File("C:\\Users\\Administrator\\Desktop\\device.xlsx");
		deviceFromExcel.readDeviceList(file).stream().forEach(System.out::println);
	}

}
