package com.cetiti.ddapv2.process.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.service.RuleService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.web.RestSecurity;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/rule")
public class RuleController {
	
	@Resource
	private RuleService ruleService;
	
	@ApiOperation("新建规则")
	@RequestMapping(value="/new", method=RequestMethod.POST)
	public RestResult addRule(RuleExpression rule, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(ruleService.addRule(account, rule)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("修改规则")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestResult updateRule(RuleExpression rule, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(ruleService.updateRule(account, rule)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("删除规则")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public RestResult deleteRule(String ruleId, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(ruleService.deleteRule(account, ruleId)){
			return RestResult.defaultSuccessResult();
		}else {
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("获取规则列表")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public RestResult getRule(RuleExpression rule, HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		List<RuleExpression> rules = ruleService.getRuleList(account, rule);
		return null==rules?RestResult.defaultFailResult(MessageContext.getMsg())
				:RestResult.defaultSuccessResult(rules);
		
	}

}
