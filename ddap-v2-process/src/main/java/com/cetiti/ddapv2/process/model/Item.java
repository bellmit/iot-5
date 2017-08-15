package com.cetiti.ddapv2.process.model;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月14日
 * 
 */
public class Item {
	
	private String label;
	private String key;
	private Object value;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "Item [label=" + label + ", key=" + key + ", value=" + value + "]";
	}
	
}
