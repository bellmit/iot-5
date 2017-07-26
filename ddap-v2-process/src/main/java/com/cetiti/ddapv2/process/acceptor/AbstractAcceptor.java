package com.cetiti.ddapv2.process.acceptor;

import org.springframework.jmx.export.annotation.ManagedAttribute;

import com.cetiti.ddapv2.process.util.IpPortUtil;

import io.netty.channel.ChannelHandler;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年5月26日
 * 
 */
public abstract class AbstractAcceptor implements Acceptor {
	
	private int port = 8080;
	private String host = IpPortUtil.SERVER_IP;
	private String name = null;
	protected int nThread = Runtime.getRuntime().availableProcessors();
	protected ChannelHandler channelHandler;
	
    public AbstractAcceptor(String name, String host, int port, int nThread) {
		if(null!=host){
			this.host = host;
		}
		if(port>0){
			this.port = port;
		}
		if(nThread>0){
			this.nThread = nThread;
		}
		if(null==name){
			this.name = getPort()+"accptor";
		}else {
			this.name = name;
		}
	}
    @ManagedAttribute
    @Override
	public int getPort() {
		return this.port;
	}
    @ManagedAttribute
    @Override
	public String getHost(){
		return this.host;
	}
    
    @ManagedAttribute
    @Override
	public String getName(){
		return this.name;
	}
    
    @Override
    public String getInfo() {
    	return String.format("%s: %s:%d", getName(), getHost(), getPort());
    }
    
    public ChannelHandler getChannelHandler() {
		return channelHandler;
	}

	public void setChannelHandler(ChannelHandler channelHandler) {
		this.channelHandler = channelHandler;
	}
}
