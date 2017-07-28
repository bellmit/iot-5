/*
 * File Name: EncryptUtil.java
 * Copyright: Copyright 2014-2014 CETC52 CETITI All Rights Reserved.
 * Description: 
 * Author: Wuwuhao
 * Create Date: 2016-6-15

 * Modifier: Wuwuhao
 * Modify Date: 2016-6-15
 * Bugzilla Id: 
 * Modify Content: 
 */
package com.cetiti.ddapv2.process.util;

import java.security.MessageDigest;

/**
 * 〈一句话功能简述〉
 * 
 * @author Wuwuhao
 * @version WSERP V1.0.0, 2016-6-15
 * @see
 * @since WSERP V1.0.0
 */

public class EncryptUtil {
	
	private static final String SALT_KEY = "20170728";
	private static final String SALT_SECRET = "82707102";
	
	private static final String PREFIX_USER_KEY = "uk";
	private static final String PREFIX_USER_SECRET = "us";
	private static final String PREFIX_PRODUCT_KEY = "pk";
	private static final String PREFIX_PRODUCT_SECRET = "ps";
	private static final String PREFIX_DEVICE_KEY = "dk";
	private static final String PREFIX_DEVICE_SECRET = "ds";
	
	public static String md5(String s) {
		String retn = null;
		if (null == s || s.length() < 1) {
			return retn;
		}
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			byte[] bytes = md5.digest(s.getBytes("UTF-8"));
			retn = toHexString(bytes);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return retn;
	}

	private static String toHexString(byte[] bytes) {
		StringBuilder retn = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				retn.append("0");
			}
			retn.append(hex);
		}

		return retn.toString();
	}
	
	public static String generateKey(Object obj){
		int seed = null==obj?0:obj.hashCode();
		return md5(SALT_KEY+SequenceGenerator.next()+seed);
	}
	public static String generateSecret(Object obj){
		int seed = null==obj?0:obj.hashCode();
		return md5(SALT_SECRET+SequenceGenerator.next()+seed);
	}
	
	public static String generateUserKey(Object obj){
		return PREFIX_USER_KEY+generateKey(obj);
	}
	public static String generateUserSecret(Object obj){
		return PREFIX_USER_SECRET+generateSecret(obj);
	}
	
	public static String generateProductKey(Object obj){
		return PREFIX_PRODUCT_KEY+generateKey(obj);
	}
	public static String generateProductSecret(Object obj){
		return PREFIX_PRODUCT_SECRET+generateSecret(obj);
	}
	
	public static String generateDeviceKey(Object obj){
		return PREFIX_DEVICE_KEY+generateKey(obj);
	}
	public static String generateDeviceSecret(Object obj){
		return PREFIX_DEVICE_SECRET+generateSecret(obj);
	}
}
