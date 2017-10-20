package com.cetiti.ddapv2.process.util;

import org.apache.http.conn.HttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年8月8日
 * 
 */
public class IdleConnectionEvictor {

	private static final Logger LOGGER = LoggerFactory.getLogger(IdleConnectionEvictor.class);

	@Autowired
	private HttpClientConnectionManager connMgr;

	@Scheduled(fixedRate = 600 * 1000)
	public void evict() {
		if (null != connMgr) {
			connMgr.closeExpiredConnections();
		}
		LOGGER.info("evict expired http connections");
	}

}
