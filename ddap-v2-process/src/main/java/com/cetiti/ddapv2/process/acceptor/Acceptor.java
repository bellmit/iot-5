package com.cetiti.ddapv2.process.acceptor;

public interface Acceptor extends Runnable {
	
	int getPort();
	
	String getHost();
	
	String getName();
	
	String getInfo();

	void stop();
}
