package com.cetiti.ddapv2.process.spi;

import java.util.Map;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月11日
 * 
 */
public interface Codec {
	
	/*判断数据与编解码实现与数据流是否匹配*/
	boolean isMatch(String id);
	
	Map<String, Object> decode(byte[] payload);
	
	byte[] encode(String command);
}
