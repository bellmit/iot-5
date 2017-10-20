package com.cetiti.ddapv2.process;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年10月19日
 * https://github.com/spring-projects/spring-boot/blob/master/spring-boot-samples/spring-boot-sample-web-static/pom.xml
 * https://github.com/xujijun/my-spring-boot/tree/master/src/main/resources/public
 */
@SpringBootApplication
public class BootSpring {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		
		DOMConfigurator.configureAndWatch("log4j.xml");
		//http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/beans.html
		
		ConfigurableApplicationContext ctx = SpringApplication.run(new Object[]{BootSpring.class, "spring/*.xml"}, args);
		
		Processer processer = (Processer) ctx.getBean("processer");
        processer.start();
        
        HtmlAdaptorServer adaptorServer = (HtmlAdaptorServer)ctx.getBean("htmlAdaptorServer");
		adaptorServer.start();
   
        LOGGER.info("processer startup.");
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	@Override
			public void run() {
				processer.stop();
				adaptorServer.stop();
				ctx.close();
				LOGGER.info("processer shutdown.");
			}
        });
	}
}
