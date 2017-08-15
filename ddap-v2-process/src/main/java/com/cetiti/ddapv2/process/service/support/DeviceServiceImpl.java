package com.cetiti.ddapv2.process.service.support;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cetiti.ddapv2.process.dao.DeviceDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Device;
import com.cetiti.ddapv2.process.service.DeviceService;
import com.cetiti.ddapv2.process.util.EncryptUtil;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月15日
 * 
 */
public class DeviceServiceImpl implements DeviceService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeviceServiceImpl.class);
	@Resource
	private DeviceDao deviceDao;
	@Resource
	private MessageUtil msgUtil;
	
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
			LOGGER.error("addDevice [{}]", e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean updateDevice(Account account, Device device) {
		if(null==account||null==account.getAccount()
				||null==device||null==device.getId()){
			return false;
		}
		Device dbDevice = deviceDao.selectDevice(device.getId());
		if(null==dbDevice){
			MessageContext.setMsg(msgUtil.get("parameter.not.exist", device.getId()));
			return false;
		}
		if(!account.isAdmin()&&!account.getAccount().equals(dbDevice.getOwner())){
			MessageContext.setMsg(msgUtil.get("permission.deny"));
			return false;
		}
		try{
			deviceDao.updateDevice(dbDevice);
		}catch (Exception e) {
			MessageContext.setMsg(msgUtil.get("db.exception"));
			LOGGER.error("updateDevice [{}]", e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public boolean deleteDevice(Account account, String deviceId) {
		if(null==account||null==account.getId()||null==deviceId){
			return false;
		}
		Device device = deviceDao.selectDevice(deviceId);
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
			LOGGER.error("deleteDevice [{}]", e.getMessage());
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
			LOGGER.error("getDeviceList [{}]", e.getMessage());
		}
		return new ArrayList<>();
	}

	@Override
	public int cmd(Account account, Device device, String cmd) {
		// TODO Auto-generated method stub
		return 0;
	}

}
