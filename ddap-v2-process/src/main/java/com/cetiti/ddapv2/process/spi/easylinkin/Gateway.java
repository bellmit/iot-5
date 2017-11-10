package com.cetiti.ddapv2.process.spi.easylinkin;

public class Gateway {
	
	private String fcntdown;
	private String fcntup;
	private String gweui;
	private String rssi;
	private String lsnr;
	private String alti;
	private String lng;
	private String lati;
	
	public String getFcntdown() {
		return fcntdown;
	}
	public void setFcntdown(String fcntdown) {
		this.fcntdown = fcntdown;
	}
	public String getFcntup() {
		return fcntup;
	}
	public void setFcntup(String fcntup) {
		this.fcntup = fcntup;
	}
	public String getGweui() {
		return gweui;
	}
	public void setGweui(String gweui) {
		this.gweui = gweui;
	}
	public String getRssi() {
		return rssi;
	}
	public void setRssi(String rssi) {
		this.rssi = rssi;
	}
	public String getLsnr() {
		return lsnr;
	}
	public void setLsnr(String lsnr) {
		this.lsnr = lsnr;
	}
	public String getAlti() {
		return alti;
	}
	public void setAlti(String alti) {
		this.alti = alti;
	}
	public String getLng() {
		return lng;
	}
	public void setLng(String lng) {
		this.lng = lng;
	}
	public String getLati() {
		return lati;
	}
	public void setLati(String lati) {
		this.lati = lati;
	}
	@Override
	public String toString() {
		return "LoraGateway [fcntdown=" + fcntdown + ", fcntup=" + fcntup + ", gweui=" + gweui + ", rssi=" + rssi
				+ ", lsnr=" + lsnr + ", alti=" + alti + ", lng=" + lng + ", lati=" + lati + "]";
	}
	
	
}
