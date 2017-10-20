package com.cetiti.ddapv2.process.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModelProperty;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public class DBModel {
	
	public static final char STATE_NEW = 'N';
	public static final char STATE_CHECKED = 'C';
	public static final char STATE_LOCK = 'L';
	public static final char STATE_DELETE = 'D';
	
	public static final String PREFIX_DEVICE = "D";
	public static final String PREFIX_PRODUCT = "P";
	public static final String PREFIX_RULE = "R";
	
	protected String id;
	@ApiModelProperty(hidden=true)
	private char dataState;
	@ApiModelProperty(hidden=true)
	private Date createTime;
	@ApiModelProperty(hidden=true)
	private Date updateTime;
	
	public static boolean isLegalDataState(char state){
		return STATE_NEW==state||STATE_CHECKED==state||
				STATE_LOCK==state||STATE_DELETE==state;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public char getDataState() {
		return dataState;
	}
	public void setDataState(char dataState) {
		this.dataState = dataState;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		if(StringUtils.hasText(this.id)){
			map.put("id", this.id);
		}
		return map;
	}
	
	@Override
	public String toString() {
		return "DBModel [id=" + id + ", dataState=" + dataState + ", createTime=" + createTime + ", updateTime="
				+ updateTime + "]";
	}
	
}
