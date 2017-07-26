package com.cetiti.ddapv2.process.spi;

import java.util.ServiceLoader;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通过SPI的方式加载数据的编解码实现
 * http://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html
 * 
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月11日
 * 
 */
public class CodecFactory {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CodecFactory.class);
	
	private static ServiceLoader<Codec> codecLoader = ServiceLoader.load(Codec.class);
	
	public static Codec getCodec(String id) {
		for(Codec codec:codecLoader){
			if(codec.isMatch(id)){
				return codec;
			}
		}
		
		LOGGER.error("can not find service provider [{}]", id);
		return null;
	}
	
	/**
	 * 提供运行时动态加载机制
	 */
	public static void reload(){
		codecLoader.reload();
	}
}
