package com.junlaninfo.netty.client;

import java.net.InetSocketAddress;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class nettyClient {
  public static void main(String[] args) {

      //创建nioEventLoopGroup
      NioEventLoopGroup group = new NioEventLoopGroup();
      Bootstrap bootstrap = new Bootstrap();
      bootstrap.group(group).channel(NioSocketChannel.class)
              .remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
              .handler(new ChannelInitializer<SocketChannel>() {
                  @Override
                  protected void initChannel(SocketChannel ch) throws Exception {
                      ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                      ch.pipeline().addLast(new StringEncoder());
                      ch.pipeline().addLast(new ClientHandler());
                  }
              });
      try {
          // 发起同步连接
          ChannelFuture sync = bootstrap.connect().sync();
          sync.channel().closeFuture().sync();
      } catch (Exception e) {

      } finally {
          group.shutdownGracefully();
      }

  
}
}
