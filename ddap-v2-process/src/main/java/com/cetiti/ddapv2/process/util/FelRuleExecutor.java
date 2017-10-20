package com.cetiti.ddapv2.process.util;

import java.util.Map;
import java.util.Map.Entry;

import com.greenpineyu.fel.FelEngine;
import com.greenpineyu.fel.FelEngineImpl;
import com.greenpineyu.fel.context.FelContext;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月31日
 * fel
 * http://www.iteye.com/news/26533-fast-expression-language
 */
public class FelRuleExecutor implements RuleExecutor {

	@Override
	public boolean isDataLegal(String rule, Map<String, Object> data) {
		if(null==rule||rule.length()<1){
			return true;
		}
		FelEngine fel = new FelEngineImpl();
		FelContext ctx = fel.getContext();
		if(null!=data&&data.size()>0){
			for(Entry<String, Object>entry : data.entrySet()){
				ctx.set(entry.getKey(), entry.getValue());
			}
		}
		Object ret = fel.eval(rule);
		if(ret instanceof Boolean){
			return (boolean)ret;
		}
		
		return true;
	}

}
