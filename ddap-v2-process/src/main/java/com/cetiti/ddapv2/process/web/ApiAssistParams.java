package com.cetiti.ddapv2.process.web;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月20日
 * 
 */
@ApiModel
public class ApiAssistParams {
	
	private static final int ELE_LIFE_TIME_IN_MINUTES = 3;
	
	private static Cache<String, Long> reqHistory = 
			CacheBuilder.newBuilder().expireAfterWrite(ELE_LIFE_TIME_IN_MINUTES, TimeUnit.MINUTES).build();
	
	@ApiModelProperty(required=true)
	private long timestamp;
	@ApiModelProperty(required=true)
	private String randomStr;
	@ApiModelProperty(required=true)
	private String key;
	@ApiModelProperty(required=true)
	private String sign;
	@ApiModelProperty(hidden=true)
	private String token;
	@ApiModelProperty(hidden=true)
	private String version;
	@ApiModelProperty(hidden=true)
	private Map<String, Object> params;

	public boolean isValidReq() {
		if (!StringUtils.hasText(this.key)) {
			MessageContext.setMsg("[key] null");
			return false;
		}
		if (timestamp<=0) {
			MessageContext.setMsg("[timestamp] invalid");
			return false;
		}
		if (!StringUtils.hasText(this.sign)) {
			MessageContext.setMsg("[sign] null");
			return false;
		}
		String key = this.key + this.timestamp + this.randomStr;
		if (null != reqHistory.getIfPresent(key) || System.currentTimeMillis() - timestamp
				> TimeUnit.MINUTES.toMillis(ELE_LIFE_TIME_IN_MINUTES)) {
			MessageContext.setMsg("request expired");
			return false;
			
		}
		reqHistory.put(key, timestamp);
		return true;
	}
	
	public static void registRequest(String key, long timestamp){
		if(null==key){
			return;
		}
		reqHistory.put(key, timestamp);
	}
	
	public static boolean isRequestExpired(String key, long timestamp){
		if(null==key){
			return false;
		}
		return null != reqHistory.getIfPresent(key) || System.currentTimeMillis() - timestamp
				> TimeUnit.MINUTES.toMillis(ELE_LIFE_TIME_IN_MINUTES);
	}

	public boolean checkSign(String secret) {
		if (!isValidReq()) {
			return false;
		}
		/*String origin = request.getHeader("origin");
		LOGGER.debug("origin：" + origin);
		if (StringUtils.isNotBlank(origin) && origin.matches("^http(s)?://((.+.)+.+)|localhost")
				&& app.getServiceId().indexOf(origin) >= 0) {
			response.setHeader("Access-Control-Allow-Origin", origin);
		}*/

		StringBuilder sb = new StringBuilder();
		sb.append(secret);
		for (Entry<String, Object> entry : toMap().entrySet()) {
			if (null != entry.getValue()&&!"sign".equals(entry.getKey())) {
				sb.append(entry.getKey()).append(entry.getValue().toString().trim());
			}
		}
		sb.append(secret);
		String serverSign = EncryptUtil.md5(sb.toString());
		if (!serverSign.equalsIgnoreCase(this.sign)) {
			MessageContext.setMsg("sign error, md5("+sb.toString()+") = "+serverSign);
			return false;
		}
		
		return true;
	}
	
	public String generateSign(String secret){
		StringBuilder sb = new StringBuilder();
		sb.append(secret);
		for (Entry<String, Object> entry : toMap().entrySet()) {
			if (null != entry.getValue()&&!"sign".equals(entry.getKey())) {
				sb.append(entry.getKey()).append(entry.getValue().toString().trim());
			}
		}
		sb.append(secret);
		return EncryptUtil.md5(sb.toString());
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getRandomStr() {
		return randomStr;
	}

	public void setRandomStr(String randomStr) {
		this.randomStr = randomStr;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new TreeMap<>();
		if (this.timestamp > 0) {
			map.put("timestamp", this.timestamp);
		}
		if (StringUtils.hasText(randomStr)) {
			map.put("randomStr", this.randomStr);
		}
		if (StringUtils.hasText(this.key)) {
			map.put("key", this.key);
		}
		if (StringUtils.hasText(this.sign)) {
			map.put("sign", this.sign);
		}
		if (StringUtils.hasText(this.token)) {
			map.put("token", this.token);
		}
		if (StringUtils.hasText(this.version)) {
			map.put("version", this.version);
		}
		if (null != params) {
			map.putAll(params);
		}

		return map;
	}

	@Override
	public String toString() {
		return "ApiAssistParams [timestamp=" + timestamp + ", randomStr=" + randomStr + ", key=" + key + ""
				+ ", token=" + token + ", version=" + version + ", params=" + params + "]";
	}

}
