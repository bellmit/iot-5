package com.cetiti.ddapv2.process.util;

import java.util.Map;

import com.googlecode.aviator.AviatorEvaluator;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月31日
 * aviator
 * http://www.blogjava.net/killme2008/archive/2011/09/17/358863.html
 * https://github.com/killme2008/aviator/wiki
 */
public class AviatorRuleExecutor implements RuleExecutor {

	@Override
	public boolean isDataLegal(String rule, Map<String, Object> data) {
		if(null==rule||rule.length()<1){
			return true;
		}
		Object ret = AviatorEvaluator.execute(rule, data, true);
		
		if(ret instanceof Boolean){
			return (boolean)ret;
		}
		
		return true;
	}

}
