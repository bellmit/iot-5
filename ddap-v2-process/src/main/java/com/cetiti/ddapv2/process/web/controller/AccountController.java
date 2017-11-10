package com.cetiti.ddapv2.process.web.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cetiti.ddapv2.process.model.Account;
import com.cetiti.ddapv2.process.model.Page;
import com.cetiti.ddapv2.process.service.AccountService;
import com.cetiti.ddapv2.process.util.MessageContext;
import com.cetiti.ddapv2.process.util.MessageUtil;
import com.cetiti.ddapv2.process.util.RestResult;
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
		if(null==retn){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		RestSecurity.writeSession(retn, request);
		return RestResult.defaultSuccessResult(retn);
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
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		return RestResult.defaultSuccessResult(account);		
	}
	
	@ApiOperation("获取用户详情")
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public RestResult getAccount(String account, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		who = accountService.getAccount(account, who);
		if(null==who){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		return RestResult.defaultSuccessResult(who);		
	}
	
	@ApiOperation("用户注册")
	@RequestMapping(value="/regist", method=RequestMethod.POST)
	public RestResult regist(Account account){
		Account retn = accountService.addAccount(account);
		if(null==retn){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		return RestResult.defaultSuccessResult(retn);
	}
	
	@ApiOperation("更新账号信息")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public RestResult update(Account account, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		if(accountService.updateAccount(account, who)){
			return RestResult.defaultSuccessResult();
		}
		return RestResult.defaultFailResult();
	}
	
	@ApiOperation("删除账号")
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public RestResult deleteAccount(String account, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		if(accountService.deleteAccount(account, who)){
			return RestResult.defaultSuccessResult();
		}
		return RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("获取用户列表")
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public RestResult getAccountList(Account account, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		List<Account> retn = accountService.getAccountList(account, who);
		if(null==retn){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		return RestResult.defaultSuccessResult(retn);
	}
	
	@ApiOperation("分页获取用户列表")
	@RequestMapping(value="/page", method=RequestMethod.GET)
	public RestResult getAccountPage(Account account, Page<Account> page, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		Page<Account> retn = accountService.getAccountPage(account, page, who);
		if(null==retn){
			return RestResult.defaultFailResult(MessageContext.getMsg());
		}
		return RestResult.defaultSuccessResult(retn);
	}
	
	@ApiOperation("用户自己修改密码")
	@RequestMapping(value="/changepassword", method=RequestMethod.POST)
	public RestResult changePassword(String oldPassword, String newPassword, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		who.setPassword(oldPassword);
		boolean retn = accountService.changePassword(newPassword, who);
		if(retn){
			return RestResult.defaultSuccessResult();
		}
		return RestResult.defaultFailResult(MessageContext.getMsg());
	}
	
	@ApiOperation("管理员重置用户密码")
	@RequestMapping(value="/resetpassword", method=RequestMethod.POST)
	public RestResult resetPassword(String account, HttpServletRequest request){
		Account who = RestSecurity.getSessionAccount(request);
		boolean retn = accountService.resetPassword(account, who);
		if(retn){
			return RestResult.defaultSuccessResult();
		}
		return RestResult.defaultFailResult(MessageContext.getMsg());
	}

}
