package com.cetiti.ddapv2.process.model;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月16日
 * 
 */
public class Thing extends DBModel{

	private String id;
	private String name;
	private String description;
	private String owner;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	
}
