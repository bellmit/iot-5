package com.cetiti.ddapv2.process.model;

import java.util.List;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月16日
 * 
 */
public class Thing extends DBModel{

	private String name;
	private String description;
	private String descAttributes;
	private List<Item> descAttributeList;
	private String owner;
	
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
	public String getDescAttributes() {
		return descAttributes;
	}
	public void setDescAttributes(String descAttributes) {
		this.descAttributes = descAttributes;
	}
	public List<Item> getDescAttributeList() {
		return descAttributeList;
	}
	public void setDescAttributeList(List<Item> descAttributeList) {
		this.descAttributeList = descAttributeList;
	}
	
	@Override
	public String toString() {
		return "Thing [name=" + name + ", description=" + description + ", descAttributes=" + descAttributes
				+ ", descAttributeList=" + descAttributeList + ", owner=" + owner + ", id=" + id + ", getDataState()="
				+ getDataState() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
	
}
