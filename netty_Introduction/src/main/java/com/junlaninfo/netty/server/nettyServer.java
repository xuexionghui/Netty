package com.junlaninfo.netty.server;



import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class nettyServer {
  private  static  int port =8080;
	public static void main(String[] args) {
		//服务端要创建两个线程池 一个是工作线程池和boss线程池
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new  NioEventLoopGroup();
	    ServerBootstrap serverBootstrap = new  ServerBootstrap();
	    serverBootstrap.group(bossGroup, workGroup)
	    //选择服务器通道
	    .channel(NioServerSocketChannel.class)
	    //处理消息
	    .childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				//解决粘包的问题
				ch.pipeline().addLast(new LineBasedFrameDecoder(1024));
                ch.pipeline().addLast(new StringEncoder());
				// 绑定hander
				 ch.pipeline().addLast(new  ServerHanlder());
			}
		});
	    //绑定我们的端口号码 单独创建线程处理，但是整个过程是同步
	    ChannelFuture channelFuture;
		try {
			channelFuture = serverBootstrap.bind(port).sync();
			 System.out.println("服务器启动成功" + port);
		        channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			 // 优雅的关闭线程池
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
		}
	}

}
