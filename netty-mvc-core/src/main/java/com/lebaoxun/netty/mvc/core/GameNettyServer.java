package com.lebaoxun.netty.mvc.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.lebaoxun.netty.mvc.core.message.MessageDecoder;
import com.lebaoxun.netty.mvc.core.message.MessageEncoder;
import com.lebaoxun.netty.mvc.netty.ServerHandler;
import com.lebaoxun.netty.mvc.netty.ServerInitializer;

@Component
public class GameNettyServer {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	/** 用于分配处理业务线程的线程组个数 */
	protected static final int BIZGROUPSIZE = Runtime.getRuntime().availableProcessors() * 2; 
	

	@Resource
	private ServerInitializer initializer;
	
	@Value("${app.server.port}")
	private int port;
	
	/** 业务出现线程大小 */
	@Value("${app.server.theads}")
	private int theads = 4;
	
	@Bean
	public boolean run() throws Exception {
		EventLoopGroup bossGroup = new NioEventLoopGroup(BIZGROUPSIZE);  
        EventLoopGroup workerGroup = new NioEventLoopGroup(theads);  
        ByteBuf heapBuffer = Unpooled.buffer(8);  
        heapBuffer.writeBytes("\r".getBytes());  
        try {
            ServerBootstrap b = new ServerBootstrap(); // (2)  
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class) // (3)  
                .childHandler(new ChannelInitializer<SocketChannel>() { // (4)  
                            @Override  
                            public void initChannel(SocketChannel ch) throws Exception {  
                                ch.pipeline().addLast("encoder", new MessageEncoder());
                                ch.pipeline().addLast("decoder", new MessageDecoder());
                                ch.pipeline().addFirst(new LineBasedFrameDecoder(65535))  
                                        .addLast(new ServerHandler());  
                            }  
                        }).option(ChannelOption.SO_BACKLOG, 1024) // (5)  
                .childOption(ChannelOption.SO_KEEPALIVE, true); // (6)  
            logger.info("socket.io server started at port " + this.port + '.');
            ChannelFuture f = b.bind(port).sync(); // (7)  
            f.channel().closeFuture().sync();
        } finally {  
            workerGroup.shutdownGracefully();  
            bossGroup.shutdownGracefully();  
        }  
		return true;
	}
}
