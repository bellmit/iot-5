package com.cetiti.ddapv2.process.model;

import java.sql.Date;

import com.cetiti.ddapv2.process.util.SequenceGenerator;

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
	
	private String id = SequenceGenerator.next();
	private char dataState;
	private Date createTime;
	private Date updateTime;
	
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
	
}
