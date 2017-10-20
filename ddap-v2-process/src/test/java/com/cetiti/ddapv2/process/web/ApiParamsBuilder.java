package com.cetiti.ddapv2.process.web;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月20日
 * 
 */
public class ApiParamsBuilder {

	private String key = "ukcb96f4be242e891ff80e6e6c3805681c";
	private String secret = "us46f8d57022061b570c1897901ea869f2";
	
	public ApiParamsBuilder(String key, String secret) {
		this.key = key;
		this.secret = secret;
	}

	public Map<String, Object> build(Map<String, Object> params){
		Map<String, Object> map = new TreeMap<>();
		if(null!=params){
			map.putAll(params);
		}
		map.put("timestamp", System.currentTimeMillis());
		map.put("randomStr", UUID.randomUUID().toString());
	    map.put("key", this.key);
		map.put("sign", generateSign(this.secret, map));
		return map;
	}

	private String generateSign(String secret, Map<String, Object> map){
		StringBuilder sb = new StringBuilder();
		sb.append(secret);
		for (Entry<String, Object> entry : map.entrySet()) {
			if (null != entry.getValue()&&!"sign".equals(entry.getKey())) {
				sb.append(entry.getKey()).append(entry.getValue().toString().trim());
			}
		}
		sb.append(secret);
		//System.out.println(sb.toString());
		return md5(sb.toString());
	}
	
	private String md5(String s) {
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

	private String toHexString(byte[] bytes) {
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

}
