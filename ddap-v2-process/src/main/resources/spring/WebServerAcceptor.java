package com.cetiti.ddapv2.process.web;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.NCSARequestLog;
import org.eclipse.jetty.server.NetworkTrafficServerConnector;
import org.eclipse.jetty.server.RequestLog;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.RequestLogHandler;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.util.thread.ThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cetiti.ddapv2.process.acceptor.AbstractAcceptor;

/**
 * set up an embedded Jetty appropriately whether
 * running in an IDE or in "production" mode in a shaded jar.
 * 
 */
public class WebServerAcceptor extends AbstractAcceptor{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WebServerAcceptor.class);
	private static final String NAME = "ddap-webserver";
	private static final  int MIN_THREAD_NUM = 10;
	// TODO: You should configure this appropriately for your environment
	private static final String LOGPATH = "./logs/webAccess/yyyymmdd.request.log";

	private static final String WEBXML = "webapp/WEB-INF/web.xml";
	private static final String CLASSONLYAVAILABLEINIDE = "com.cetiti.ddapv2.process.web.WebServerAcceptorTest";
	private static final String PROJECTRELATIVEPATHTOWEBAPP = "src/main/java/webapp";

	private Server server;
	

	public WebServerAcceptor(int port) {
		this(null, port);
	}

	public WebServerAcceptor(String host, int port) {
		this(host, port, 0);
	}
	
	public WebServerAcceptor(String host, int port, int nthread) {
		super(NAME, host, port, nthread);
		if(this.nThread<MIN_THREAD_NUM) {
			this.nThread = 5*MIN_THREAD_NUM;
		}
	}
	
	@Override
	public void run() {
		try {
			start();
			System.out.println(NAME + " start." + getInfo());
			LOGGER.info(NAME + " startup. [{}]", getInfo());
			join();
		} catch (Exception e) {
			LOGGER.error(NAME + "interrupt. [{}]", e.getMessage());
		}
	}

	public void start() throws Exception {
		server = new Server(createThreadPool());

		NetworkTrafficServerConnector connector = createConnector();
		server.addConnector(connector);

		server.setHandler(createHandlers());
		server.setStopAtShutdown(true);

		server.start();
	}

	public void join() throws InterruptedException {
		server.join();
	}

	public void stop() {
		try{
			server.stop();
		}catch (Exception e) {
			LOGGER.error("WebServerAcceptor stoped.[{}]", e.getMessage());
		}
	}

	private ThreadPool createThreadPool() {
		QueuedThreadPool threadPool = new QueuedThreadPool();
		threadPool.setMinThreads(MIN_THREAD_NUM);
		threadPool.setMaxThreads(this.nThread);
		return threadPool;
	}

	private NetworkTrafficServerConnector createConnector() {
		NetworkTrafficServerConnector connector = new NetworkTrafficServerConnector(server);
		connector.setPort(getPort());
		connector.setHost(getHost());
		return connector;
	}

	private HandlerCollection createHandlers() {
		WebAppContext ctx = new WebAppContext();
		ctx.setContextPath("/");

		if (isRunningInShadedJar()) {
			ctx.setWar(getShadedWarUrl());
		} else {
			ctx.setWar(PROJECTRELATIVEPATHTOWEBAPP);
		}

		List<Handler> handlers = new ArrayList<Handler>();

		handlers.add(ctx);

		HandlerList contexts = new HandlerList();
		contexts.setHandlers(handlers.toArray(new Handler[0]));

		RequestLogHandler log = new RequestLogHandler();
		log.setRequestLog(createRequestLog());

		HandlerCollection result = new HandlerCollection();
		result.setHandlers(new Handler[] { contexts, log });

		return result;
	}

	private RequestLog createRequestLog() {
		NCSARequestLog log = new NCSARequestLog();

		File logPath = new File(LOGPATH);
		logPath.getParentFile().mkdirs();

		log.setFilename(logPath.getPath());
		log.setRetainDays(90);
		log.setExtended(false);
		log.setAppend(true);
		log.setLogTimeZone("GMT");
		log.setLogLatency(true);
		return log;
	}

	// ---------------------------
	// Discover the war path
	// ---------------------------

	private boolean isRunningInShadedJar() {
		try {
			Class.forName(CLASSONLYAVAILABLEINIDE);
			return false;
		} catch (ClassNotFoundException anExc) {
			return true;
		}
	}

	private URL getResource(String aResource) {
		return Thread.currentThread().getContextClassLoader().getResource(aResource);
	}

	private String getShadedWarUrl() {
		String urlStr = getResource(WEBXML).toString();
		// Strip off "WEB-INF/web.xml"
		return urlStr.substring(0, urlStr.length() - 15);
	}
	
	public static void main(String[] args) throws Exception {
		Class.forName(CLASSONLYAVAILABLEINIDE);
	}

}
