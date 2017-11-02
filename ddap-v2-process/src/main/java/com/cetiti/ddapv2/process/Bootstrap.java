package com.cetiti.ddapv2.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年5月26日
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-spring-mvc-static-content
 */
@SpringBootApplication
public class Bootstrap {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);
	
	public static void main(String[] args) {
		//SpringApplicationBuilder
		ConfigurableApplicationContext ctx = SpringApplication.run(new Object[]{Bootstrap.class, "spring/*.xml"}, args);
		
		Processer processer = (Processer) ctx.getBean("processer");
        processer.start();
  
        LOGGER.info("processer startup.");
        Runtime.getRuntime().addShutdownHook(new Thread(){
        	@Override
			public void run() {
				processer.stop();
				ctx.close();
				LOGGER.info("processer shutdown.");
			}
        });
	}
}
