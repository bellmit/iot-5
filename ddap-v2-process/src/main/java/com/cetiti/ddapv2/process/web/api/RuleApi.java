package com.cetiti.ddapv2.process.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.service.RuleService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.web.ApiAssistParams;
import com.cetiti.ddapv2.process.web.DateConverter;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年10月11日
 * 
 */
@Api(value="/api/rule", description="告警规则管理接口")
@RestController
@RequestMapping("/api/rule")
public class RuleApi {
	
	@Resource
	private RuleService ruleService;
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private DateConverter dateConverter;
	
	@ApiOperation("添加告警规则")
	@RequestMapping(value="/v1/new", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult addRule(RuleExpression rule, ApiAssistParams assistParams) {
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		if(null!=rule){
			assistParams.setParams(rule.toMap());
		}
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		
		return  ruleService.addRule(account, rule)?
				RestResult.defaultSuccessResult():RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("删除告警规则")
	@RequestMapping(value="/v1/delete", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult deleteRule(String ruleId, ApiAssistParams assistParams) {
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		if(null!=ruleId){
			Map<String, Object> map = new HashMap<>();
			map.put("ruleId", ruleId);
			assistParams.setParams(map);
		}
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		
		return  ruleService.deleteRule(account, ruleId)?
				RestResult.defaultSuccessResult():RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("更新告警规则")
	@RequestMapping(value="/v1/update", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult updateRule(RuleExpression rule, ApiAssistParams assistParams) {
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		if(null!=rule){
			assistParams.setParams(rule.toMap());
		}
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		
		return  ruleService.updateRule(account, rule)?
				RestResult.defaultSuccessResult():RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("查询告警规则")
	@RequestMapping(value="/v1/list", method={RequestMethod.GET,RequestMethod.POST})
	public RestResult getRule(@ApiParam(value = "yyyy-MM-dd hh:mm:ss") String updateTime, RuleExpression rule, 
			ApiAssistParams assistParams, Page<RuleExpression> page) {
		if(null==assistParams||!StringUtils.hasText(assistParams.getKey())) {
			return RestResult.defaultFailResult(msgUtil.get("parameter.null", "key"));
		}
		Account account = accountService.getAccountByKey(assistParams.getKey());
		if(null==account){
			return RestResult.defaultFailResult(msgUtil.get("parameter.not.exist", "key"));
		}
		if(null==rule){
			rule = new RuleExpression();
		}
		Map<String, Object> map = new HashMap<>();
		if(page.getPageNum()>0){
			map.put("pageNum", page.getPageNum());
		}
		if(page.getPageSize()>0){
			map.put("pageSize", page.getPageSize());
		}
		if(StringUtils.hasText(updateTime)){
			map.put("updateTime", updateTime);
			rule.setUpdateTime(dateConverter.convert(updateTime));
		}
		map.putAll(rule.toMap());
		assistParams.setParams(map);
		if(!assistParams.checkSign(account.getUserSercret())){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		rule.setOwner(account.getAccount());
		Page<RuleExpression> rules = ruleService.getRulePage(account, rule, page);
		return null==rules?RestResult.defaultFailResult(MessageContext.getMsg())
				:RestResult.defaultSuccessResult(rules);
	}

}
