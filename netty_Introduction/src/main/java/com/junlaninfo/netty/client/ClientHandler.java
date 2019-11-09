package com.junlaninfo.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class ClientHandler  extends SimpleChannelInboundHandler  {

	 /**
     * 活跃的通道
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 10; i++) {
        	//设置边界
            ctx.writeAndFlush(Unpooled.copiedBuffer("我是客户端，服务端你能收到吗?\n", CharsetUtil.UTF_8));
        }

    }

    /**
     * 接受消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String resp = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("客户端接受服务器端消息:" + resp);
    }

}
