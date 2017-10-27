package com.cetiti.ddapv2.process.web;

import java.io.File;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月26日
 * 
 */
@Configuration
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebAppConfig.class);
	
	@Value("${spring.resources.static-locations}")
	private String staticLocations;
	
    /* 27.1.5
     * @see https://stackoverflow.com/questions/26720090/a-simple-way-to-implement-swagger-in-a-spring-mvc-application
     * https://stackoverflow.com/questions/21123437/how-do-i-use-spring-boot-to-serve-static-content-located-in-dropbox-folder
     */
    @Override 
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
    	System.out.println("staticLocation: "+staticLocations);
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        String location = getStaticLocations();
        if(null!=location){
        	registry.addResourceHandler("*.html").addResourceLocations(location+"html/");
            registry.addResourceHandler("/resources/**").addResourceLocations(location+"resources/");
        }else{
        	registry.addResourceHandler("*.html").addResourceLocations("classpath:/static/html/");
            registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");
        }
    }
    
    private String getStaticLocations(){
    	if(!StringUtils.hasText(staticLocations)){
    		return null;
    	}
    	if(!staticLocations.endsWith("/")){
    		staticLocations += "/";
    	}
    	if(staticLocations.indexOf("://")>0){
    		try{
    			URI uri = new URI(staticLocations);
    			return uri.toString();
    		}catch (Exception e) {
				LOGGER.error("spring.resources.static-locations [{}] is not a valid uri.", staticLocations);
			}
    		return null;
    	}
    	File file = new File(staticLocations);
    	if(!file.exists()){
    		LOGGER.error("spring.resources.static-locations [{}] file doesn't exist.", staticLocations);
    		return null;
    	}
    	return file.toURI().toString();
    }
    
}
