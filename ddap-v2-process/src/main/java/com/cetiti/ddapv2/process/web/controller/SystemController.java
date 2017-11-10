package com.cetiti.ddapv2.process.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.acceptor.hk8200.CrossingDao;
import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.web.RestSecurity;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/sys")
public class SystemController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SystemController.class);
	
	@Resource
	private CrossingDao crossingDao;
	@Resource
	private MessageUtil msgUtil;
	
	@ApiOperation("同步更新卡口数据")
	@RequestMapping(value="/updatecrossing", method=RequestMethod.POST)
	public RestResult updataDeviceMetaData(String productId, HttpServletRequest request) {
		Account account = RestSecurity.getSessionAccount(request);
		if(null==account||!account.isAdmin()||CrossingDao.CROSSIONG_OWNER.equals(account.getAccount())){
			return RestResult.defaultFailResult(msgUtil.get("permission.deny"));
		}
		LOGGER.info("sync update crossing data.");
		crossingDao.fullUpdate();
		return RestResult.defaultSuccessResult();
	}

}
