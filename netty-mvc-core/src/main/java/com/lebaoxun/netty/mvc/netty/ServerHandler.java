package com.lebaoxun.netty.mvc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lebaoxun.netty.mvc.core.invote.ActionHandlerContainer;
import com.lebaoxun.netty.mvc.core.message.Header;
import com.lebaoxun.netty.mvc.core.message.Message;

public class ServerHandler extends SimpleChannelInboundHandler<Object> {
	
	private Logger logger = LoggerFactory.getLogger(ServerHandler.class);
    
    @Override  
    public void channelActive(ChannelHandlerContext ctx) throws Exception {  
        String content="我收到连接";
        Header header= new Header((byte)0, (byte)1, (byte)1, (byte)1, (byte)0, "713f17ca614361fb257dc6741332caf2",content.getBytes("UTF-8").length);  
        Message message=new Message(header,"welcome",content);
        
        logger.info("channelActive|message={},header={}",message,header);
        ctx.writeAndFlush(message);  
    }  
  
    @Override  
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {  
        cause.printStackTrace();  
        ctx.close();  
    }  

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		// TODO Auto-generated method stub
		
		 Message m = (Message) msg; // (1)  
        /* 请求分发*/  
         ActionHandlerContainer.invote(m.getCammand(),ctx, m);  
	}  
}  