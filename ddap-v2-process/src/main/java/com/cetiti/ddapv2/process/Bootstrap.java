package com.cetiti.ddapv2.process;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sun.jdmk.comm.HtmlAdaptorServer;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年5月26日
 * 
 */
public class Bootstrap {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		
		DOMConfigurator.configureAndWatch("log4j.xml");
		//http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/beans.html
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/*.xml");
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
