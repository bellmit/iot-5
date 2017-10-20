package com.cetiti.ddapv2.process.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
    /* 
     * @see https://stackoverflow.com/questions/26720090/a-simple-way-to-implement-swagger-in-a-spring-mvc-application
     * https://stackoverflow.com/questions/21123437/how-do-i-use-spring-boot-to-serve-static-content-located-in-dropbox-folder
     */
    @Override 
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        registry.addResourceHandler("*.html").addResourceLocations("classpath:/static/html/");
        registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");
    }
}
