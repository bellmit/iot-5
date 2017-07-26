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
package com.cetiti.ddapv2.api.hiktech;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.cetiti.ddapv2.api.hiktech.Instruction.SystemType;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


/**
 * @Description TODO
 * @author Wuwuhao
 * @date 2017年6月7日
 * 
 */
public class ElectromobileHandler extends ChannelInboundHandlerAdapter {

    private final ByteBuf message;
    static final int SIZE = 256;
    private ChannelHandlerContext channelHandlerContext;
    
    /**
     * Creates a client-side handler.
     */
    public ElectromobileHandler() {
        message = Unpooled.buffer(SIZE);
        message.writeBytes(Instruction.createClientRegisterInstruction(SystemType.ElectroMobile, (byte)0x35));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        ctx.writeAndFlush(message);
        channelHandlerContext = ctx;
        startup();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
    	if(msg instanceof RfidData){
    		System.out.println("RfidData");
    		System.out.println(msg);
    	}else if(msg instanceof RfidAlarm){
    		System.out.println("RfidAlarm");
    		System.out.println(msg);
    	}else if(msg instanceof RfidState){
    		System.out.println("RfidState");
    		System.out.println(msg);
    	}else {
    		System.out.println("null type");
    		System.out.println(msg);
		}
     
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
        shutdown();
    }
    
    private ScheduledExecutorService ses; 
	
	private Runnable timerTask = new Runnable() {
		@Override
		public void run() {
			try {
				 ByteBuf alive = Unpooled.buffer(27);
				 alive.writeBytes(Instruction.createClientKeepaliveInstruction(SystemType.ElectroMobile, (byte)0x35));
				 channelHandlerContext.writeAndFlush(alive);
				 System.out.println("Timer working");
			} catch (Exception e) {
				System.err.println("Timer exception. "+e.getMessage());
			}
		}
	};
	
	public void startup(){
		if(null==ses){
			ses = Executors.newSingleThreadScheduledExecutor();
			ses.scheduleAtFixedRate(timerTask, 10, 50, TimeUnit.SECONDS);
		}
	}
	
	public void shutdown(){
		if(null!=ses){
			ses.shutdown();
		}
	}
}
