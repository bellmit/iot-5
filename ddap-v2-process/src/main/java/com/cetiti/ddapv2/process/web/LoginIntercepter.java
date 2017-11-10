package com.cetiti.ddapv2.process.web;

import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cetiti.ddapv2.process.util.JsonUtil;
import com.cetiti.ddapv2.process.util.RestResult;
import com.cetiti.ddapv2.process.util.RestResult.CODE;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年9月30日
 * 
 * https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc
 */
@Component
public class LoginIntercepter extends HandlerInterceptorAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginIntercepter.class);
	private Set<String> bypassApiSet = new HashSet<>();
	
	@Resource
	private JsonUtil jsonUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		String uri = request.getRequestURI();
    	String context = request.getContextPath();
    	String subUri = uri.substring(uri.indexOf(context)+context.length());
    	
		if(handler instanceof HandlerMethod){
			/*HandlerMethod hm = (HandlerMethod)handler;
			Method method = hm.getMethod();*/
	    	if(!isLogedIn(request)&&!bypassApiSet.contains(subUri)){
    			RestResult result = new RestResult(CODE.RET_NOT_LOGGED_IN, "session expired", null);
	    		try {
	    			response.setContentType("application/json");
	    			PrintWriter out = response.getWriter();
	    			out.print(jsonUtil.toJson(result));
	        		out.flush();
	        		out.close();
	    		} catch (Exception e) {
	    			LOGGER.error("write response [{}] to [{}] exception [{}]", result, subUri, e.getMessage());
	    		}
	    		return false;
	    	}
		}
		
		if(subUri.indexOf(".html")>0&&!isLogedIn(request)){
			response.sendRedirect("login.html");
		}

		return true;
	}
	
	private boolean isLogedIn(HttpServletRequest request) {
		return null!=RestSecurity.getSessionAccount(request);
	}

	public Set<String> getBypassApiSet() {
		return bypassApiSet;
	}

	public void setBypassApiSet(Set<String> bypassApiSet) {
		if(null!=bypassApiSet){
			this.bypassApiSet = bypassApiSet;
		}
	}

}
