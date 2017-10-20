package com.cetiti.ddapv2.process.service.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.dao.DataDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.cetiti.ddapv2.process.service.DataService;
import com.cetiti.ddapv2.process.service.RuleService;
import com.cetiti.ddapv2.process.util.HttpClient;
import com.cetiti.ddapv2.process.util.JsonUtil;
import com.cetiti.ddapv2.process.util.LocalCache;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月18日
 * 
 */
@Service
public class DataServiceImpl implements DataService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);
	
	@Resource
	private DataDao dataDao;
	@Resource
	private JsonUtil jsonUtil;
	@Resource
	private MessageUtil msgUtil;
	@Resource(name="processHttpClient")
	private HttpClient httpClient;
	@Resource
	private LocalCache cache;
	@Resource
	private RuleService ruleService;
	
	@Override
	public void process(Data data) {
		if(!isDataValid(data)||!isDataChanged(data)){
			return;
		}
		
		store(data);
		analyze(data);
	}

	@Override
	public void store(Data data) {
		try{
			dataDao.insertData(data.getDeviceId(), jsonUtil.toJson(data.getMapData()));
			cache.setData(data.getDeviceId(), data);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("store [{}] exception [{}]", data, e.getMessage());
		}
	}

	@Override
	public void analyze(Data data) {
		for(RuleExpression rule:getRules(data.getDeviceId())){
			boolean legal = ruleService.isDataLegal(rule.getExpression(), data.getMapData());
			if(!legal){
				data.setWarningState(Data.STATE_WARNING);
				data.setWarningRuleId(rule.getId());
				dispatch(data, rule.getOwner());
			}
		}
	}

	private void dispatch(Data data, String account) {
		Account user = cache.getAccount(account);
		if(null==user){
			return;
		}
		httpClient.post(user.getDataPostUrl(), data);
	}

	private boolean isDataValid(Data data) {
		if(null==data||!StringUtils.hasText(data.getDeviceId())||
				null==data.getMapData()||data.getMapData().size()<1){
			MessageContext.setMsg(data+"invalid");
			return false;
		}
		return true;
	}
	
	private boolean isDataChanged(Data data) {
		String dataStr = jsonUtil.toJson(data.getMapData());
		return null!=dataStr&&!dataStr.equals(cache.getData(data.getDeviceId()));
	}
	
	private List<RuleExpression> getRules(String deviceId) {
		List<RuleExpression> rules = new ArrayList<>();
		rules.addAll(cache.getRules(cache.getProductId(deviceId)));
		rules.addAll(cache.getRules(deviceId));
		return rules;
	}
	
	private List<RuleExpression> getRules(String deviceId, String account) {
		if(null==account){
			return new ArrayList<>();
		}
		return getRules(deviceId).stream().filter(rule->
		account.equals(rule.getOwner())).collect(Collectors.toList());
	}

	@Override
	public Map<String, Object> latestData(String deviceId, Account account) {
		if(null==account||null==account.getAccount()){
			return null;
		}
		Device device = cache.getDevice(deviceId);
		if(null==device){
			MessageContext.setMsg("device don't exist.");
			return null;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(device.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return null;
		}
		Data data = cache.getData(deviceId);
		if(null==data){
			return null;
		}
		if(null==data.getMapData()){
			data.setMapData(jsonUtil.mapFromJson(data.getStrData(), String.class, Object.class));
		}
		for(RuleExpression rule:getRules(deviceId, account.getAccount())){
			if(!ruleService.isDataLegal(rule.getExpression(), data.getMapData())){
				data.setWarningState(Data.STATE_WARNING);
				data.setWarningRuleId(rule.getId());
			}
		}
		return data.toMap();
	}

	@Override
	public Page<Map<String, Object>> historyData(String deviceId, Date beginTime,
			Date endTime, Page<Map<String, Object>> page, Account account) {
		if(null==account||null==account.getAccount()||null==beginTime){
			return null;
		}
		Device device = cache.getDevice(deviceId);
		if(null==device){
			MessageContext.setMsg("device don't exist.");
			return null;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(device.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return null;
		}
		
		CompletableFuture<List<Map<String, Object>>> dataFuture = new CompletableFuture<>();
		new Thread(() -> {
			try{
				List<Map<String, Object>> mlist = new ArrayList<>();
				List<Data> list = dataDao.selectData(deviceId, beginTime, endTime, page);
				if(null!=list){
					for(Data data:list){
						data.setMapData(jsonUtil.mapFromJson(data.getStrData(), String.class, Object.class));
						mlist.add(data.toMap());
					}
				}
				dataFuture.complete(mlist);
			}catch (Exception e) {
				MessageContext.setMsg(msgUtil.get("db.exception"));
				LOGGER.error("dataDao.selectData deviceId[{}] from [{}] to [{}] page[{}] exception [{}]", 
						deviceId, beginTime, endTime, page, e.getMessage());
				dataFuture.completeExceptionally(e);
			}
		}).start();
		int count = 0;
		try{
			count = dataDao.countData(deviceId, beginTime, endTime);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("dataDao.countData deviceId[{}] from [{}] to [{}] exception [{}]", 
					deviceId, beginTime, endTime, e.getMessage());
		}
		page.setTotal(count);
		try {
			page.setList(dataFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("page.setList [{}]", e.getMessage());
			return null;
		}
				
		return page;
	}
	
}
