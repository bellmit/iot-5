package com.cetiti.ddapv2.process.model;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Data {
	
	private String deviceId;
	
	private byte[] rawData;
	
	private String strData;
	
	private List<Map<String, Object>> lmData;
	
	private List<DataItem> objData;
	
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

	public List<Map<String, Object>> getLmData() {
		return lmData;
	}

	public void setLmData(List<Map<String, Object>> lmData) {
		this.lmData = lmData;
	}

	public List<DataItem> getObjData() {
		return objData;
	}

	public void setObjData(List<DataItem> objData) {
		this.objData = objData;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Data [deviceId=" + deviceId + ", rawData=" + Arrays.toString(rawData) + ", strData=" + strData
				+ ", timeStamp=" + timeStamp + "]";
	}
	
	

}
