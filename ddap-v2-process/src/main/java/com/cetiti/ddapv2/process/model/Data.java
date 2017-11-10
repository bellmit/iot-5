package com.cetiti.ddapv2.process.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class Data {
	
	public static final char STATE_NORMAL = '0';
	public static final char STATE_WARNING = '1';
	
	private String deviceId;

	private byte[] rawData;
	
	private String strData;
	
	private Map<String, Object> mapData;
	
	private List<DataItem> objData;
	
	private char warningState = STATE_NORMAL;
	
	private String warningRuleId;
	
	private int timeStamp;
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	public String getStrData() {
		return strData;
	}

	public void setStrData(String strData) {
		this.strData = strData;
	}

	public Map<String, Object> getMapData() {
		return mapData;
	}

	public void setMapData(Map<String, Object> mapData) {
		this.mapData = mapData;
	}

	public List<DataItem> getObjData() {
		return objData;
	}

	public void setObjData(List<DataItem> objData) {
		this.objData = objData;
	}

	public char getWarningState() {
		return warningState;
	}

	public void setWarningState(char warningState) {
		this.warningState = warningState;
	}
	
	public String getWarningRuleId() {
		return warningRuleId;
	}

	public void setWarningRuleId(String warningRuleId) {
		this.warningRuleId = warningRuleId;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("deviceId", this.deviceId);
		if(null!=this.mapData){
			map.putAll(this.mapData);
		}
		map.put("warningState", this.warningState);
		if(StringUtils.hasText(warningRuleId)){
			map.put("warningRuleId", this.warningRuleId);
		}
		if(timeStamp==0){
			this.timeStamp = (int)System.currentTimeMillis()/1000;
		}
		map.put("timeStamp", this.timeStamp);
		return map;
	}

	@Override
	public String toString() {
		return "Data [deviceId=" + deviceId + ", rawData=" + Arrays.toString(rawData) + ", strData=" + strData
				+ ", mapData=" + mapData + ", objData=" + objData + ", warningState=" + warningState
				+ ", warningRuleId=" + warningRuleId + ", timeStamp=" + timeStamp + "]";
	}
	
}
