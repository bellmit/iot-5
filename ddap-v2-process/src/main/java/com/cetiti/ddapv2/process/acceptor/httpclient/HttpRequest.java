package com.cetiti.ddapv2.process.acceptor.httpclient;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.http.client.config.RequestConfig;
import com.cetiti.ddapv2.process.model.Data;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月7日
 * 
 */
public class HttpRequest {
	
	static final String METHOD_POST = "POST";
	static final String METHOD_GET = "GET";
	
	//请求的接口地址
	private String apiUrl;
	//接口参数
	private Map<String, Object> paramMap;
	//http请求的方法， 支持POST和GET
	private String httpMethod;
	//请求超时时间、代理等客制化配置
	private RequestConfig requestConfig;
	/**
	 * 请求时间规划
	 * <a href="http://www.manpagez.com/man/5/crontab/">Crontab pattern</a>
	 * @see org.springframework.scheduling.support.CronSequenceGenerator
	 */
	private String cron;
	//接口参数可能由接口动态提供
	private Supplier<Map<String, Object>> paramSupplier;
	//接口数据转换，将接口返回的数据转化为平台通用的SmartthingMessage
	private Function<String, Data> function;
	
	
	public String getApiUrl() {
		return apiUrl;
	}
	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}
	public Map<String, Object> getParamMap() {
		return paramMap;
	}
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public RequestConfig getRequestConfig() {
		return requestConfig;
	}
	public void setRequestConfig(RequestConfig requestConfig) {
		this.requestConfig = requestConfig;
	}
	public String getCron() {
		return cron;
	}
	public void setCron(String cron) {
		this.cron = cron;
	}
	public Supplier<Map<String, Object>> getParamSupplier() {
		return paramSupplier;
	}
	public void setParamSupplier(Supplier<Map<String, Object>> paramSupplier) {
		this.paramSupplier = paramSupplier;
	}
	public Function<String, Data> getFunction() {
		return function;
	}
	public void setFunction(Function<String, Data> function) {
		this.function = function;
	}

	@Override
	public String toString() {
		return "HttpRequest [apiUrl=" + apiUrl + ", paramMap=" + paramMap + ", httpMethod=" + httpMethod
				+ ", requestConfig=" + requestConfig + ", cron=" + cron + ", paramSupplier=" + paramSupplier
				+ ", function=" + function + "]";
	}

}
