package com.cetiti.ddapv2.process.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.cetiti.ddapv2.process.dao.AccountDao;
import com.cetiti.ddapv2.process.dao.DataDao;
import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.dao.ProductDao;
import com.cetiti.ddapv2.process.dao.RuleDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Data;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Product;
import com.cetiti.ddapv2.process.model.RuleExpression;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;

@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
@Component
public class LocalCache {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LocalCache.class);
	private static final int MAX_SIZE = 1000;
	private static final int EXPIRE_IN_MINUTE = 30;
	
	@Resource
	private DataDao dataDao;
	@Resource
	private ProductDao productDao;
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private RuleDao ruleDao;
	@Resource
	private AccountDao accountDao;
	
	public LocalCache() {
		System.out.println("************LocalCache initiated***************");
	}

	private LoadingCache<String, Data> dataCache = CacheBuilder.newBuilder()
			.maximumSize(MAX_SIZE)
			.expireAfterAccess(EXPIRE_IN_MINUTE, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Data>() {

				@Override
				public Data load(String deviceId) throws Exception {
					return dataDao.selectLatestData(deviceId);
				}
			});
	
	private LoadingCache<String, Product> productCache = CacheBuilder.newBuilder()
			.maximumSize(MAX_SIZE)
			.expireAfterAccess(EXPIRE_IN_MINUTE, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Product>() {

				@Override
				public Product load(String productId) throws Exception {
					return productDao.selectProduct(productId);
				}
			});
	
	private LoadingCache<String, Device> deviceCache = CacheBuilder.newBuilder()
			.maximumSize(MAX_SIZE)
			.expireAfterAccess(EXPIRE_IN_MINUTE, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Device>() {

				@Override
				public Device load(String deviceId) throws Exception {
					return deviceDao.selectDevice(deviceId);
				}
			});
	
	private LoadingCache<String, List<RuleExpression>> ruleCache = CacheBuilder.newBuilder()
			.maximumSize(MAX_SIZE)
			.expireAfterAccess(EXPIRE_IN_MINUTE, TimeUnit.MINUTES)
			.build(new CacheLoader<String, List<RuleExpression>>() {

				@Override
				public List<RuleExpression> load(String deviceIdOrProductId) throws Exception {
					return ruleDao.selectRuleList(deviceIdOrProductId);
				}
			});
	
	private LoadingCache<String, Account> accountCache = CacheBuilder.newBuilder()
			.maximumSize(MAX_SIZE)
			.expireAfterAccess(EXPIRE_IN_MINUTE, TimeUnit.MINUTES)
			.build(new CacheLoader<String, Account>() {

				@Override
				public Account load(String account) throws Exception {
					return accountDao.selectAccount(account);
				}
			});
	
	public Data getData(String deviceId){
		if(!StringUtils.hasText(deviceId)){
			return null;
		}
		try{
			return dataCache.get(deviceId);
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getData [{}] exception [{}]", deviceId, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return null;
	}
	
	public void setData(String deviceId, Data data){
		if(StringUtils.hasText(deviceId)&&null!=data){
			dataCache.put(deviceId, data);
		}
	}
	
	/**
	 * get product by deviceId from cache
	 * @param deviceId
	 * @return
	 */
	public Product getProduct(String deviceId){
		if(!StringUtils.hasText(deviceId)){
			return null;
		}
		try{
			return productCache.get(getProductId(deviceId));
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getProduct [{}] exception [{}]", deviceId, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return null;
	}
	
	public void removeProduct(String productId){
		if(null!=productId){
			productCache.invalidate(productId);
		}
	}
	
	public void removeDevice(String deviceId){
		if(null!=deviceId){
			deviceCache.invalidate(deviceId);
		}
	}
	
	public String getProductId(String deviceId){
		if(!StringUtils.hasText(deviceId)){
			return null;
		}
		try{
			Device device = deviceCache.get(deviceId);
			if(null!=device){
				return device.getProductId();
			}
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getDeviceId [{}] exception [{}]", deviceId, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return null;
	}
	
	public Device getDevice(String deviceId){
		if(!StringUtils.hasText(deviceId)){
			return null;
		}
		try{
			return deviceCache.get(deviceId);
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getDevice [{}] exception [{}]", deviceId, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return null;
	}
	
	public List<RuleExpression> getRules(String deviceIdOrProductId){
		if(!StringUtils.hasText(deviceIdOrProductId)){
			return new ArrayList<>();
		}
		try{
			return ruleCache.get(deviceIdOrProductId);
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getRules [{}] exception [{}]", deviceIdOrProductId, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return new ArrayList<>();
	}
	
	public void removeRules(String deviceIdOrProductId){
		if(null!=deviceIdOrProductId){
			ruleCache.invalidate(deviceIdOrProductId);
		}
	}
	
	public Account getAccount(String account){
		if(!StringUtils.hasText(account)){
			return null;
		}
		try{
			return accountCache.get(account);
		}catch (ExecutionException | UncheckedExecutionException e) {
			LOGGER.error("getAccount [{}] exception [{}]", account, e.getMessage());
		}catch (InvalidCacheLoadException e) {
			//null value
		}
		return null;
	}
	
	public void removeAccount(String account){
		if(null!=account){
			accountCache.invalidate(account);
		}
	}
	
	public static void main(String[] args){
		LocalCache cache = new LocalCache();
		System.out.println(cache.getData("123"));
	}

}
