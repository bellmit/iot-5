package com.cetiti.ddapv2.process.dao;

import java.util.List;

import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;

public interface RuleDao {

	int insertRule(RuleExpression rule);
	
	int deleteRule(String ruleId);
	
	int updateRule(RuleExpression rule);
	
	RuleExpression selectRule(String rule);
	
	List<RuleExpression> selectRuleList(RuleExpression rule);
	
	List<RuleExpression> selectRuleList(String deviceIdOrProductId);
	
	List<RuleExpression> selectRuleList(RuleExpression rule, Page<?> page);
	
	int countRule(RuleExpression rule);
}
