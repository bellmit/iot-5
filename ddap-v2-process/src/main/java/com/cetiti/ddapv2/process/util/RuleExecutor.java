package com.cetiti.ddapv2.process.util;

import java.util.Map;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月31日
 * 
 */
public interface RuleExecutor {
	
	boolean isDataLegal(String rule, Map<String, Object> data);

}
