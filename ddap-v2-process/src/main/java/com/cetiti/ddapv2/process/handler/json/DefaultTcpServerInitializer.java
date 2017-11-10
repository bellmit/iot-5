package com.cetiti.ddapv2.process.handler.json;

import javax.annotation.Resource;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.util.CharsetUtil;

public class DefaultTcpServerInitializer extends ChannelInitializer<SocketChannel> {
	
	@Resource(name="defaultTcpServerHandler")
	private DefaultTcpServerHandler defaultTcpServerHandler;
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new JsonObjectDecoder(true));
        p.addLast(new StringDecoder(CharsetUtil.UTF_8));
        p.addLast(defaultTcpServerHandler);
		
	}

}
