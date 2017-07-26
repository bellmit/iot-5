package com.cetiti.ddapv2.process.acceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TcpClientAcceptor extends AbstractAcceptor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TcpClientAcceptor.class);
	private EventLoopGroup group;

	public TcpClientAcceptor(String name, String host, int port) {
		super(name, host, port, 0);
	}

	@Override
	public void run() {
		group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .option(ChannelOption.TCP_NODELAY, true)
             .handler(new LoggingHandler(LogLevel.INFO))
             .handler(channelHandler);

            // Start the client.
            ChannelFuture f = b.connect(getHost(), getPort()).sync();
            LOGGER.info("{} start up at [{}:{}]", getName(), getHost(), getPort());
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
        	LOGGER.error("failed to start up {} at [{}:{}], message:{}", 
					 getName(), getHost(), getPort(), e.getMessage());
		} finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
	}
	
	@ManagedOperation
	@Override
	public void stop() {
		if(null!=group){
			group.shutdownGracefully();
		}
	}
}
