package com.cetiti.ddapv2.process.web;

import static org.junit.Assert.*;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class WebServerAcceptorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Ignore
	public void testStart() {
		try {
			WebServerAcceptor server = new WebServerAcceptor(8888);
			server.start();
			System.out.println("server start.");
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Ignore
	public void testJetty() throws Exception {
		String path = WebServerAcceptorTest.class.getResource("/").getPath()+"webapp";
		path = path.substring(1);
		System.out.println(path);
		Server server = new Server(8081);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("D:/tmp");
		
		server.setHandler(resourceHandler);
		
		server.start();
		
		System.out.println("server startup");
	}

}
