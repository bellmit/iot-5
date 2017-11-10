package com.cetiti.ddapv2.process.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DataBuilder {
	
	public static final char STATE_NORMAL = '0';
	public static final char STATE_ABNORMAL = '1';
	
	private static final String ANTI_DISMANTLE_STATE = "antiDismantleState";
	private static final String WORKING_STATE = "workingState";
	private static final String POWER_STATE = "powerState";
	private Map<String, Object> map;
	private Data data;
	
	public DataBuilder(String deviceId){
		Objects.requireNonNull(deviceId);
		data = new Data();
		map = new HashMap<>();
		data.setDeviceId(deviceId);
	}
	
	public DataBuilder setAntiDismantleState(char state){
		map.put(ANTI_DISMANTLE_STATE, state);
		return this;
	}
	
	public DataBuilder setWorkingState(char state){
		map.put(WORKING_STATE, state);
		return this;
	}
	
	public DataBuilder setPowerState(char state){
		map.put(POWER_STATE, state);
		return this;
	}
	
	public DataBuilder setData(Map<String, Object> data){
		map.putAll(data);
		return this;
	}
	
	public DataBuilder setData(String key, Object value){
		map.put(key, value);
		return this;
	}
	
	public Data build(){
		data.setMapData(map);
		return data;
	}
}
