package com.cetiti.ddapv2.process.acceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.cetiti.ddapv2.process.util.IpPortUtil;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.Future;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年5月26日
 * 
 */
@ManagedResource
public class UdpAcceptor extends AbstractAcceptor {
	
	private EventLoopGroup group;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UdpAcceptor.class);
	
	public UdpAcceptor(String name, int port) {
		this(name, IpPortUtil.SERVER_IP, port);
	}
	
	public UdpAcceptor(String name, String host, int port) {
		this(name, host, port, 0);
	}
	
	public UdpAcceptor(String name, String host, int port, int nThread) {
		super(name, host, port, nThread);
	}
	
	@Override
	public void run() {
		group = new NioEventLoopGroup(this.nThread);

        try {
            final Bootstrap boot = new Bootstrap();
            boot.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .handler(channelHandler);
            // Start the server.
            final ChannelFuture future = boot.bind(getHost(), getPort()).sync();
            LOGGER.debug("{} start up at [{}:{}]", getName(), getHost(), getPort());
            // Wait until the server socket is closed.
            future.channel().closeFuture().sync();
        }catch (Exception e) {
        	LOGGER.error("failed to start up {} at [{}:{}], message:{}", 
					getName(), getHost(), getPort(), e.getMessage());
		} finally {
            // Shut down all event loops to terminate all threads.
        	group.shutdownGracefully();
		}
	}

	@Override
	public void stop() {
		if(null!=group){
			Future<?> future = group.shutdownGracefully();
			future.syncUninterruptibly();
		}
	}
	
}
