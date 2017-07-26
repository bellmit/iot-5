package com.cetiti.ddapv2.api.hiktech;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年6月12日
 * 
 */

public class RfidData {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RfidData.class);
	
	private String epc;
	private int battery;
	private String rssi;
	private long captureTime;
	private String collectionEquipmentId;
	private String collectionEquipmentLongitude;
	private String collectionEquipmentLatitude;
	private int isEnter;
	private long auxliaryTime;
	private String epcType;
	
	public RfidData() {
		
	}
	
	public RfidData(String data) {
		if(null==data){
			return;
		}
		String[] arr = data.split(Instruction.ELEMENT_SEPERATOR);
		if(arr.length>0){
			this.epc = arr[0];
		}
		if(arr.length>1){
			try{
				this.battery = Integer.parseInt(arr[1]);
			}catch (NumberFormatException e) {
				LOGGER.error("init battery error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>2){
			this.rssi = arr[2];
		}
		if(arr.length>3){
			try{
				this.captureTime = Long.parseLong(arr[3]);
			}catch (NumberFormatException e) {
				LOGGER.error("init captureTime error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>4){
			this.collectionEquipmentId = arr[4];
		}
		if(arr.length>5){
			this.collectionEquipmentLongitude = arr[5];
		}
		if(arr.length>6){
			this.collectionEquipmentLatitude = arr[6];
		}
		if(arr.length>7){
			try{
				this.isEnter = Integer.parseInt(arr[7]);
			}catch (NumberFormatException e) {
				LOGGER.error("init isEnter error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>8){
			try{
				this.auxliaryTime = Long.parseLong(arr[8]);
			}catch (NumberFormatException e) {
				LOGGER.error("init auxliaryTime error, message[{}], data[{}]", e.getMessage(), data);
			}
		}
		if(arr.length>9){
			this.epcType = arr[9];
		}
	}
	
	public static void main(String[] args){
		String data = "37028400277228	100		1497255666	2046825226	103.666057	30.996717	0	1497255666";
		RfidData rfid = new RfidData(data);
		System.out.println(rfid);
	}
	
	public String getEpc() {
		return epc;
	}
	public void setEpc(String epc) {
		this.epc = epc;
	}
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public long getCaptureTime() {
		return captureTime;
	}
	public void setCaptureTime(long captureTime) {
		this.captureTime = captureTime;
	}
	public String getCollectionEquipmentId() {
		return collectionEquipmentId;
	}
	public void setCollectionEquipmentId(String collectionEquipmentId) {
		this.collectionEquipmentId = collectionEquipmentId;
	}
	public String getCollectionEquipmentLongitude() {
		return collectionEquipmentLongitude;
	}
	public void setCollectionEquipmentLongitude(String collectionEquipmentLongitude) {
		this.collectionEquipmentLongitude = collectionEquipmentLongitude;
	}
	public String getCollectionEquipmentLatitude() {
		return collectionEquipmentLatitude;
	}
	public void setCollectionEquipmentLatitude(String collectionEquipmentLatitude) {
		this.collectionEquipmentLatitude = collectionEquipmentLatitude;
	}
	public int getIsEnter() {
		return isEnter;
	}
	public void setIsEnter(int isEnter) {
		this.isEnter = isEnter;
	}
	public long getAuxliaryTime() {
		return auxliaryTime;
	}
	public void setAuxliaryTime(long auxliaryTime) {
		this.auxliaryTime = auxliaryTime;
	}
	public String getEpcType() {
		return epcType;
	}
	public void setEpcType(String epcType) {
		this.epcType = epcType;
	}

	@Override
	public String toString() {
		return "RfidData [epc=" + epc + ", battery=" + battery + ", rssi=" + rssi + ", captureTime=" + captureTime
				+ ", collectionEquipmentId=" + collectionEquipmentId + ", collectionEquipmentLongitude="
				+ collectionEquipmentLongitude + ", collectionEquipmentLatitude=" + collectionEquipmentLatitude
				+ ", isEnter=" + isEnter + ", auxliaryTime=" + auxliaryTime + ", epcType=" + epcType + "]";
	}
	
}
