package com.cetiti.ddapv2.process.spi.easylinkin;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FlammableGasSensorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		LoraData lora = new LoraData();
		lora.setMac("mac123");
		FlammableGasSensor sensor = new FlammableGasSensor();
		
		String[] hex = {"010000", "011100", "010200", "010250", "01020a"};
		for(String s:hex){
			lora.setData(s);
			System.out.println(sensor.decode(lora).getMapData());
		}
	}

}
