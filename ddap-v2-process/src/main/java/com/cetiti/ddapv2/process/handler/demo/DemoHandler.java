/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.cetiti.ddapv2.process.handler.demo;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年5月26日
 * 
 */
public class DemoHandler extends SimpleChannelInboundHandler<DemoObj> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DemoObj msg) throws Exception {
    	
        System.err.println("process warning " + msg.getData());
        System.err.println("process storage " + msg.getData());
        
        ChannelFuture future = ctx.writeAndFlush("data received");
        future.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				if(!f.isSuccess()){
					f.cause().printStackTrace();
					f.channel().close();
				}
			}
		});
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
