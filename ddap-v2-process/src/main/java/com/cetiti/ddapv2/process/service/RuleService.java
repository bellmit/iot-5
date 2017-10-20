package com.cetiti.ddapv2.process.service;

import java.util.List;
import java.util.Map;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;

public interface RuleService {
	
	boolean addRule(Account account, RuleExpression rule);
	
	boolean deleteRule(Account account, String ruleId);
	
	boolean updateRule(Account account, RuleExpression rule);
	
	List<RuleExpression> getRuleList(Account account, RuleExpression rule);
	
	boolean isDataLegal(String rule, Map<String, Object> data);
	
	Page<RuleExpression> getRulePage(Account account, RuleExpression rule, Page<RuleExpression> page);
	
}
