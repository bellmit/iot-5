package com.cetiti.ddapv2.process.model;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月2日
 * 
 */
public class DataItem extends Item {
	
	private String unit;
	
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return "DataItem [unit=" + unit + ", " + super.toString() + "]";
	}
	
}
