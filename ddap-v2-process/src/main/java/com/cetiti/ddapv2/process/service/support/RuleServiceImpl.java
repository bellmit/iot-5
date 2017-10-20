package com.cetiti.ddapv2.process.service.support;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.dao.RuleDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.service.RuleService;
import com.cetiti.ddapv2.process.util.LocalCache;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.googlecode.aviator.AviatorEvaluator;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月28日
 * 
 */
@Service
public class RuleServiceImpl implements RuleService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RuleServiceImpl.class);
	@Resource
	private RuleDao ruleDao;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private LocalCache cache;
	
	@Override
	public boolean addRule(Account account, RuleExpression rule) {
		if(null==account||null==account.getAccount()||null==rule){
			return false;
		}
		rule.setOwner(account.getAccount());
		try{
			ruleDao.insertRule(rule);
			cache.removeRules(rule.getDeviceId());
			cache.removeRules(rule.getProductId());
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] addRule [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
			return false;
		}
		
		return true;
	}

	@Override
	public boolean deleteRule(Account account, String ruleId) {
		if(null==account||null==account.getAccount()||null==ruleId) {
			return false;
		}
		RuleExpression rule = null;
		try{
			rule = ruleDao.selectRule(ruleId);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] deleteRule [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
			return false;
		}
		if(null==rule){
			return true;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(rule.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			ruleDao.deleteRule(ruleId);
			cache.removeRules(rule.getDeviceId());
			cache.removeRules(rule.getProductId());
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] deleteRule [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateRule(Account account, RuleExpression rule) {
		if(null==account||null==account.getAccount()
				||null==rule||null==rule.getId()){
			return false;
		}
		RuleExpression dbRule = null;
		try{
			dbRule = ruleDao.selectRule(rule.getId());
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] updateRule [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
			return false;
		}
		if(null==dbRule){
			MessageContext.setMsg(msgUtil.get("parameter.not.exist", rule.getId()));
			return false;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(dbRule.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			ruleDao.updateRule(rule);
			cache.removeRules(rule.getDeviceId());
			cache.removeRules(rule.getProductId());
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] updateRule [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<RuleExpression> getRuleList(Account account, RuleExpression rule) {
		if(null==account||null==account.getAccount()){
			return null;
		}
		if(null==rule){
			rule = new RuleExpression();
		}
		if(!account.isAdmin()){
			rule.setOwner(account.getAccount());
		}
		try{
			return ruleDao.selectRuleList(rule);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("[{}] getRuleList [{}] exception [{}]", 
					account.getAccount(), rule, e.getMessage());
		}
		return null;
	}
	
	@Override
	public Page<RuleExpression> getRulePage(Account account, RuleExpression rule, Page<RuleExpression> page) {
		if(null==account||null==account.getAccount()){
			return page;
		}
		if(!account.isAdmin()){
			rule.setOwner(account.getAccount());
		}
		CompletableFuture<List<RuleExpression>> ruleFuture = new CompletableFuture<>();
		new Thread(() -> {
			try{
				ruleFuture.complete(ruleDao.selectRuleList(rule, page));
			}catch (Exception e) {
				MessageContext.setMsg(msgUtil.get("db.exception"));
				LOGGER.error("ruleDao.selectRuleList rule[{}] page[{}] exception [{}]", 
						rule, page, e.getMessage());
				ruleFuture.completeExceptionally(e);
			}
		}).start();
		int count = 0;
		try{
			count = ruleDao.countRule(rule);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("ruleDao.countRule rule[{}] page[{}] exception [{}]", 
					rule, page, e.getMessage());
		}
		page.setTotal(count);
		try {
			page.setList(ruleFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("page.setList [{}]", e.getMessage());
			return null;
		}
				
		return page;
	}
	
	@Override
	public boolean isDataLegal(String rule, Map<String, Object> data) {
		if(!StringUtils.hasText(rule)||null==data||data.size()<1){
			return true;
		}
		Object ret = null;
		try{
			ret = AviatorEvaluator.execute(rule, data, true);
		}catch (Exception e) {
			MessageContext.setMsg("rule excution excption.");
			LOGGER.error("isDataLegal rule[{}] data[{}] exception[{}]", rule, data, e.getMessage());
			return true;
		}
		
		if(ret instanceof Boolean){
			return (boolean)ret;
		}
		
		return true;
	}

}
