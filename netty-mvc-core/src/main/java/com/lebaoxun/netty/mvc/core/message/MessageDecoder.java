package com.lebaoxun.netty.mvc.core.message;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
  
/** 
 * HeaderDecoder.java 
 *  
 * @author caiqianyi 
 * @version 1.0 
 */  
public class MessageDecoder extends ByteToMessageDecoder {
	private Logger logger = LoggerFactory.getLogger(MessageDecoder.class);
    /**包长度志头**/  
    public static final int HEAD_LENGHT = 40;  
    /**标志头**/  
    public static final byte PACKAGE_TAG = 0x01;  
    @Override  
    protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {  
        buffer.markReaderIndex();
        logger.info("buffer.readableBytes={},HEAD_LENGHT={}",buffer.readableBytes(),HEAD_LENGHT);
        if (buffer.readableBytes() < HEAD_LENGHT) {  
            throw new CorruptedFrameException("包长度问题");  
        }  
        byte tag = buffer.readByte();  
        if (tag != PACKAGE_TAG) {  
            throw new CorruptedFrameException("标志错误");
        }  
        byte encode = buffer.readByte();  
        byte encrypt = buffer.readByte();  
        byte extend1 = buffer.readByte();  
        byte extend2 = buffer.readByte();  
        byte sessionByte[] = new byte[32];  
        buffer.readBytes(sessionByte);  
        String sessionid = new String(sessionByte,"UTF-8");  
        int length = buffer.readInt();
        logger.info("length={}",length);
        byte[] data=new byte[length];  
        buffer.readBytes(data);
        String datas = new String(data,"UTF-8");
        int inf = datas.indexOf("#")+1;
        int einf = datas.indexOf("#", inf);
        String cammand = datas.substring(inf,einf);
        String d = datas.substring(einf+1);
        logger.info("cammand={},data={}",cammand,d);
        Header header = new Header(tag,encode, encrypt, extend1, extend2, sessionid, length);  
        Message message = new Message(header,cammand,d);  
        out.add(message);  
    }  
}  