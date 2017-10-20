package com.cetiti.ddapv2.process;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.cetiti.ddapv2.process.acceptor.Acceptor;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年7月20日
 * 
 */
@ManagedResource
public class Processer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Processer.class);
	
	private List<Acceptor> acceptors;
	private Thread[] workers;
	private Thread watchDog;
	private boolean stop = false;
	
	void start(){
		if(null==acceptors){
			return;
		}
		workers = new Thread[acceptors.size()];
		int i = 0;
		for(Acceptor acceptor:acceptors) {
			workers[i] = new Thread(acceptor, acceptor.getClass().getName());
			workers[i].start();
			i++;
		}
		
		watchDog = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!stop){
					try{
						Thread.sleep(60*1000);
						LOGGER.info("dog watching");
						watch();
					}catch (InterruptedException e) {
						LOGGER.error("dog died cause of [{}]", e.getMessage());
						break;
					}
				}
			}
		}, "processerWatchDog");
		watchDog.start();
	}
	
	public void stop(){
		stop = true;
		watchDog.interrupt();
		for(Acceptor acceptor:acceptors) {
			acceptor.stop();
		}
	}
	
	@ManagedAttribute
	public List<Acceptor> getacceptors() {
		return acceptors;
	}

	public void setacceptors(List<Acceptor> acceptors) {
		this.acceptors = acceptors;
	}
	
	private void watch(){
		if(null==workers||stop){
			return;
		}
		for(int i=0; i<workers.length; i++){
			if(!workers[i].isAlive()){
				for(Acceptor acceptor:acceptors){
					if(workers[i].getName().equals(acceptor.getClass().getName())){
						acceptor.stop();
						workers[i] = new Thread(acceptor, acceptor.getClass().getName());
						workers[i].start();
						LOGGER.info("restart worker [{}]", workers[i].getName());
					}
				}
			}
		}
	}
	
	@ManagedOperation(description="acceptor info")
	public String info(){
		return acceptors.stream().map(Acceptor::getInfo).collect(Collectors.joining("; "));
	}
	
}
