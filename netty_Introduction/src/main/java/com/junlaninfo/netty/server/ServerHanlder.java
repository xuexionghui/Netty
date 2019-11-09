package com.junlaninfo.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ServerHanlder extends SimpleChannelInboundHandler{

	/**
     * 读取通道 接受消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		    ByteBuf byteBuf = (ByteBuf) msg;
	        String request = byteBuf.toString(CharsetUtil.UTF_8);
	        System.out.println("request:" + request);
	      //设置边界
	        ctx.writeAndFlush(Unpooled.copiedBuffer("我是服务端，已经接收到你的消息\n", CharsetUtil.UTF_8));
		
	}

}
