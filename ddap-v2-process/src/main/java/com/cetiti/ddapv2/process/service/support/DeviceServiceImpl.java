package com.cetiti.ddapv2.process.service.support;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.service.DeviceService;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.LocalCache;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.greenpineyu.fel.parser.FelParser.relationalExpression_return;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月15日
 * 
 */
@Service
public class DeviceServiceImpl implements DeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private MessageUtil msgUtil;
	@Resource
	private LocalCache cache;
	
	@Override
	public boolean addDevice(Account account, Device device) {
		if(null==account||null==account.getAccount()||null==device){
			return false;
		}
		device.setDeviceKey(EncryptUtil.generateDeviceKey(device));
		device.setDeviceSecret(EncryptUtil.generateDeviceSecret(device));
		device.setOwner(account.getAccount());
		try{
			deviceDao.insertDevice(device);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("addDevice [{}] exception [{}] ", device, e.getMessage());
			return false;
		}
		return true;
	}
	
	@Override
	public boolean importDevices(Account account, List<Device> deviceList) {
		if(null==account||null==deviceList){
			return false;
		}
		deviceList.parallelStream().filter((Device d)->null!=d.getSerialNumber()).forEach((d)->{
			try{
				if(null==d.getOwner()){
					d.setOwner(account.getAccount());
				}
				if(0==deviceDao.updateDeviceBySerialNumberAndProductId(d)){
					deviceDao.insertDevice(d);
				}
			}catch (Exception e) {
				LOGGER.error("importDevices [{}] exception [{}] ", d, e.getMessage());
			}
		});
		return true;
	}
	
	private Device getDevice(String deviceId){
		try{
			return deviceDao.selectDevice(deviceId);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getDevice [{}] exception [{}]", deviceId, e.getMessage());
		}
		return null;
	}

	@Override
	public boolean updateDevice(Account account, Device device) {
		if(null==account||null==account.getAccount()
				||null==device||null==device.getId()){
			return false;
		}
		Device dbDevice = getDevice(device.getId());
		if(null==dbDevice){
			MessageContext.setMsg(msgUtil.get("parameter.not.exist", device.getId()));
			return false;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(dbDevice.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			deviceDao.updateDevice(device);
			if(null!=device.getProductId()&&!device.getProductId().equals(cache.getProductId(device.getId()))){
				cache.removeDevice(device.getId());
			}
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("updateDevice [{}] exception [{}]", device, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDevice(Account account, String deviceId) {
		if(null==account||null==account.getAccount()||null==deviceId){
			return false;
		}
		Device device = getDevice(deviceId);
		if(null==device){
			return true;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(device.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			deviceDao.deleteDevice(deviceId);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("deleteDevice [{}] exception [{}]", device, e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public List<Device> getDeviceList(Account account, Device device) {
		if(null==account||null==account.getAccount()){
			return new ArrayList<>();
		}
		if(null==device){
			device = new Device();
		}
		if(!account.isAdmin()){
			device.setOwner(account.getAccount());
		}
		try{
			return deviceDao.selectDeviceList(device);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("getDeviceList [{}] exception [{}] ", device, e.getMessage());
		}
		return new ArrayList<>();
	}
	
	@Override
	public Page<Device> getDevicePage(Account account, Device device, Page<Device> page) {
		if(null==account||null==account.getAccount()){
			return page;
		}
		if(!account.isAdmin()){
			device.setOwner(account.getAccount());
		}
		CompletableFuture<List<Device>> deviceFuture = new CompletableFuture<>();
		new Thread(() -> {
			List<Device> list = new ArrayList<>();
			try{
				list = deviceDao.selectDeviceList(device, page);
				deviceFuture.complete(list);
			}catch (Exception e) {
				MessageContext.setMsg(msgUtil.get("db.exception"));
				deviceFuture.completeExceptionally(e);
				LOGGER.error("getDevicePage [{}] page[{}] exception [{}]", device, page, e.getMessage());
			}
		}).start();
		int count = 0;
		try{
			count = deviceDao.countDevice(device);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("countDevice [{}] exception [{}]", device, e.getMessage());
		}
		page.setTotal(count);
		try {
			page.setList(deviceFuture.get());
		} catch (InterruptedException | ExecutionException e) {
			LOGGER.error("page.setList [{}]", e.getMessage());
		}
		return page;
	}

	@Override
	public int cmd(Account account, Device device, String cmd) {
		// TODO Auto-generated method stub
		return 0;
	}

}
