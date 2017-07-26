package com.cetiti.ddapv2.process.handler.demo;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class DemoChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
      
        p.addLast(new StringDecoder());
        p.addLast(new StringEncoder());
        p.addLast(new DemoObjDecoder());
        p.addLast(new DemoHandler());
	}

}
