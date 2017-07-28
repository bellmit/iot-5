package com.cetiti.ddapv2.process.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月13日
 * 
 */
public class Account extends DBModel {
	
	public static final String ROLE_ADMIN = "A";
	public static final String ROLE_PLATFORM = "P";
	public static final String ROLE_GUEST = "G";
	
	private String account;
	private String password;
	private String role;
	private List<String> privileges = new ArrayList<>();
	
	private String phone;
	private String email;
	private String address;
	
	private String token;
	private String userKey;
	private String userSercret;
	
	public boolean isAdmin(){
		return ROLE_ADMIN.equals(getRole());
	}
	
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<String> getPrivileges() {
		return privileges;
	}
	public void setPrivileges(List<String> privileges) {
		this.privileges = privileges;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getUserSercret() {
		return userSercret;
	}
	public void setUserSercret(String userSercret) {
		this.userSercret = userSercret;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public String toString() {
		return "Account [account=" + account + ", password=" + password + ", role=" + role + ", privileges="
				+ privileges + ", phone=" + phone + ", email=" + email + ", address=" + address + ", token=" + token
				+ ", userKey=" + userKey + ", userSercret=" + userSercret + ", id=" + id + ", getDataState()="
				+ getDataState() + ", getCreateTime()=" + getCreateTime() + ", getUpdateTime()=" + getUpdateTime()
				+ "]";
	}
	
}
