package com.cetiti.ddapv2.process.acceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedResource;

import com.cetiti.ddapv2.process.util.IpPortUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
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
public class TcpServerAcceptor extends AbstractAcceptor {
	
	private EventLoopGroup bossGroup;
	private EventLoopGroup workerGroup;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TcpServerAcceptor.class);
	
	public TcpServerAcceptor(String name, int port) {
		this(name, IpPortUtil.SERVER_IP, port);
	}
	
	public TcpServerAcceptor(String name, String host, int port) {
		this(name, host, port, 0);
	}
	
	public TcpServerAcceptor(String name, String host, int port, int nThread) {
		super(name, host, port, nThread);
	}
	
	@Override
	public void run() {
		bossGroup = new NioEventLoopGroup(this.nThread);
	    workerGroup = new NioEventLoopGroup();

	    try {
            final ServerBootstrap boot = new ServerBootstrap();
            boot.group(bossGroup, workerGroup)
            		.channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 10)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(channelHandler);
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
        	bossGroup.shutdownGracefully();
        	workerGroup.shutdownGracefully();
        }
	}

	@Override
	public void stop() {
		if(null!=bossGroup){
			Future<?> future = bossGroup.shutdownGracefully();
			future.syncUninterruptibly();
		}
		if(null!=workerGroup){
			Future<?> future = workerGroup.shutdownGracefully();
			future.syncUninterruptibly();
		}
	}
	
}
