package com.cetiti.ddapv2.process.spi.easylinkin;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoraData {
	
	public static final int DATA_TYPE_HEARTBEAT = 223;
	
	private String mac;
	private String appeui;
	@JsonProperty("last_update_time")
	private String lastUpdateTime;
	private String data;
	private String reserver;
	@JsonProperty("data_type")
	private int dataType;
	@JsonIgnore
	private List<Gateway> gateways;
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getAppeui() {
		return appeui;
	}
	public void setAppeui(String appeui) {
		this.appeui = appeui;
	}
	public String getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getReserver() {
		return reserver;
	}
	public void setReserver(String reserver) {
		this.reserver = reserver;
	}
	public int getDataType() {
		return dataType;
	}
	public void setDataType(int dataType) {
		this.dataType = dataType;
	}
	public List<Gateway> getGateways() {
		return gateways;
	}
	public void setGateways(List<Gateway> gateways) {
		this.gateways = gateways;
	}
	@Override
	public String toString() {
		return "LoraData [mac=" + mac + ", appeui=" + appeui + ", lastUpdateTime=" + lastUpdateTime + ", data=" + data
				+ ", reserver=" + reserver + ", dataType=" + dataType + ", gateways=" + gateways + "]";
	}

}
