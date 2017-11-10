package com.cetiti.ddapv2.process.spi.bar;

import java.util.HashMap;
import java.util.Map;

import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.spi.Codec;

public class BarProvider implements Codec{
	
	private static final String ID = "bar";

	@Override
	public boolean isMatch(String id) {
		return ID.equals(id);
	}

	/*@Override
	public Map<String, Object> decode(byte[] payload) {
		Map<String, Object> map = new HashMap<>();
		map.put("bar", "bar provider");
		return map;
	}

	@Override
	public byte[] encode(byte[] command) {
		return ID.getBytes();
	}*/

	@Override
	public Data decode(Object payload) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object encode(Object payload) {
		// TODO Auto-generated method stub
		return null;
	}

}
