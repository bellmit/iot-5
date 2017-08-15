package com.cetiti.ddapv2.process.web.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.web.RestResult;
import com.cetiti.ddapv2.process.web.RestSecurity;

import io.swagger.annotations.ApiOperation;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月15日
 * 
 */
@RestController
@RequestMapping("/account")
public class AccountController {
	
	@Resource
	private AccountService accountService;
	@Resource
	private MessageUtil msgUtil;
	
	@ApiOperation("用户登录")
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public RestResult login(Account account, HttpServletRequest request){
		Account retn = accountService.login(account);
		if(null!=retn){
			RestSecurity.writeSession(retn, request);
			return RestResult.defaultSuccessResult(retn, msgUtil.get("login.success"));
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("用户退出")
	@RequestMapping(value="/logout", method=RequestMethod.POST)
	public RestResult logout(HttpServletRequest request){
		RestSecurity.removeSession(request);
		return RestResult.defaultSuccessResult();
	}
	
	@ApiOperation("获取session")
	@RequestMapping(value="/session", method=RequestMethod.GET)
	public RestResult getSession(HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		if(null==account){
			return RestResult.defaultFailResult();
		}else{
			return RestResult.defaultSuccessResult(account);
		}		
	}
	
	@ApiOperation("用户注册")
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public RestResult regist(Account account){
		Account retn = accountService.addAccount(account);
		if(null!=retn){
			return RestResult.defaultSuccessResult(retn, msgUtil.get("regist.success"));
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("更新账号信息")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestResult update(Account account, HttpServletRequest request){
		if(!RestSecurity.isUserOwn(request)&&!RestSecurity.isAdmin(request)){
			return RestResult.defaultFailResult(msgUtil.get("permission.deny"));
		}
		if(accountService.updateAccount(account)){
			return RestResult.defaultSuccessResult();
		}else{
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
	}
	
	@ApiOperation("删除账号")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public RestResult deleteAccount(Account account, HttpServletRequest request){
		if(null!=account&&null!=account.getAccount()&&RestSecurity.isAdmin(request)){
			if(!accountService.deleteAccount(account.getAccount())){
				return RestResult.defaultFailResult(MessageContext.getMsg());
			}
		}
		return RestResult.defaultSuccessResult();
	}
	
	@ApiOperation("获取用户列表")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public RestResult getAccount(HttpServletRequest request){
		Account account = RestSecurity.getSessionAccount(request);
		return RestResult.defaultSuccessResult(accountService.getAccountList(account));
	}

}
