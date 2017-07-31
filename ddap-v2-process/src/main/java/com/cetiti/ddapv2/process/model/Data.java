package com.cetiti.ddapv2.process.model;

import java.util.Arrays;

public class Data {
	
	private String deviceId;
	
	private byte[] data;
	
	private int timeStamp;
	

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public int getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Data [deviceId=" + deviceId + ", data=" + Arrays.toString(data) + ", timeStamp=" + timeStamp + "]";
	}
	
}
