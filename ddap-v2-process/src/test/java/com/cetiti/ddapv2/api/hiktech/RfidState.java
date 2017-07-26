package com.cetiti.ddapv2.api.hiktech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RfidState {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RfidState.class);
	
	private String collectionEquipmentId;
	private long eventTime;
	private int eventType;
	
	public RfidState() {
	
	}
	
	public RfidState(String data) {
		if(null==data){
			return;
		}
		String[] arr = data.split(Instruction.ELEMENT_SEPERATOR);
		if(arr.length>0){
			this.collectionEquipmentId = arr[0];
		}
		if(arr.length>1){
			try{
				this.eventTime = Long.parseLong(arr[1]);
			}catch (NumberFormatException e) {
				LOGGER.error("init eventTime error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>2){
			try{
				this.eventType = Integer.parseInt(arr[2]);
			}catch (NumberFormatException e) {
				LOGGER.error("init eventType error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
	}
	
	public String getCollectionEquipmentId() {
		return collectionEquipmentId;
	}
	public void setCollectionEquipmentId(String collectionEquipmentId) {
		this.collectionEquipmentId = collectionEquipmentId;
	}
	public long getEventTime() {
		return eventTime;
	}
	public void setEventTime(long eventTime) {
		this.eventTime = eventTime;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	@Override
	public String toString() {
		return "RfidState [collectionEquipmentId=" + collectionEquipmentId + ", eventTime=" + eventTime + ", eventType="
				+ eventType + "]";
	}
}
