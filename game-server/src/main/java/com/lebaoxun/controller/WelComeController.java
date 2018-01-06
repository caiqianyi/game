package com.lebaoxun.controller;

import io.netty.channel.ChannelHandlerContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lebaoxun.netty.mvc.core.Controller;
import com.lebaoxun.netty.mvc.core.ReqestMapping;
import com.lebaoxun.netty.mvc.core.message.Message;

@Controller
@Component
public class WelComeController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@ReqestMapping(cammand="welcome")
	public String hello(ChannelHandlerContext ct,Message message){
		System.out.println("hello word");
		logger.info("cammand={}",message.getCammand());
		logger.info("data={}",message.getData());
		return "hello word";
	}
}
