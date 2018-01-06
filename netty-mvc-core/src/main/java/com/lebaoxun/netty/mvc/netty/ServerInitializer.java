package com.lebaoxun.netty.mvc.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lebaoxun.netty.mvc.core.message.MessageDecoder;
import com.lebaoxun.netty.mvc.core.message.MessageEncoder;

@Component
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
	private Logger logger = LoggerFactory.getLogger(ServerInitializer.class);
	
	@Value("${app.channel.readtimeout}")
	private int timeout = 3600;
	
	public void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		logger.info("ch={}",ch);
		pipeline.addLast("encoder", new MessageEncoder());
		pipeline.addLast("decoder", new MessageDecoder());
		pipeline.addFirst(new LineBasedFrameDecoder(65535));
		pipeline.addLast(new ServerHandler());
	}
}