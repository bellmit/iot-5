package com.cetiti.ddapv2.process.spi;

import com.cetiti.ddapv2.process.model.Data;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月11日
 * 
 */
public interface Codec<T, R> {
	
	/*判断数据与编解码实现与数据流是否匹配*/
	boolean isMatch(String productId);
	
	Data decode(T payload);
	//if R not null, then write R to response
	R encode(T payload);
}
