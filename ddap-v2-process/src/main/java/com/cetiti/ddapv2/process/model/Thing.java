package com.cetiti.ddapv2.process.model;

import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;

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
	@ApiModelProperty(hidden=true)
	private List<Item> descAttributeList;
	@ApiModelProperty(hidden=true)
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
	public Map<String, Object> toMap() {
		Map<String, Object> map = super.toMap();
		if(StringUtils.hasText(this.name)){
			map.put("name", this.name);
		}
		if(StringUtils.hasText(this.description)){
			map.put("description", this.description);
		}
		if(StringUtils.hasText(this.descAttributes)){
			if(null==descAttributeList){
				JsonUtil jsonUtil = new JsonUtil();
				descAttributeList = jsonUtil.listFromJson(this.descAttributes, Item.class);
			}
			for(Item item:descAttributeList){
				map.put(item.getKey(), item.getValue());
			}
		}
		if(StringUtils.hasText(this.owner)){
			map.put("owner", this.owner);
		}
		return map;
	}
	
	@Override
	public String toString() {
		return "Thing [name=" + name + ", description=" + description + ", descAttributes=" + descAttributes
				+ ", descAttributeList=" + descAttributeList + ", owner=" + owner + ", id=" + id + ", getDataState()="
				+ getDataState() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
	
}
