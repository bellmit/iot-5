package com.cetiti.ddapv2.api.hiktech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RfidAlarm {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RfidAlarm.class);
	
	private String epc;
	private long alarmTime;
	private String collectionEquipmentId;
	private int alarmType;
	
	public RfidAlarm() {
	
	}
	
	public RfidAlarm(String data) {
		if(null==data){
			return;
		}
		String[] arr = data.split(Instruction.ELEMENT_SEPERATOR);
		if(arr.length>0){
			this.epc = arr[0];
		}
		if(arr.length>1){
			try{
				this.alarmTime = Long.parseLong(arr[1]);
			}catch (NumberFormatException e) {
				LOGGER.error("init alarmTime error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>2){
			this.collectionEquipmentId = arr[2];
		}
		if(arr.length>3){
			try{
				this.alarmType = Integer.parseInt(arr[3]);
			}catch (NumberFormatException e) {
				LOGGER.error("init alarmType error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
	}
	
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	public long getAlarmTime() {
		return alarmTime;
	}
	public void setAlarmTime(long alarmTime) {
		this.alarmTime = alarmTime;
	}
	public String getCollectionEquipmentId() {
		return collectionEquipmentId;
	}
	public void setCollectionEquipmentId(String collectionEquipmentId) {
		this.collectionEquipmentId = collectionEquipmentId;
	}
	public int getAlarmType() {
		return alarmType;
	}
	public void setAlarmType(int alarmType) {
		this.alarmType = alarmType;
	}

}
