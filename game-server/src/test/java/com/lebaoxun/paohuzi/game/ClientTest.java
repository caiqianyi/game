package com.lebaoxun.paohuzi.game;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.lebaoxun.netty.mvc.core.message.Header;
import com.lebaoxun.netty.mvc.core.message.Message;
  
public class ClientTest {  
  
    public static void main(String[] args) {  
        try {  
            // 连接到服务器  
            Socket socket = new Socket("127.0.0.1", 8081);  
  
            try {  
                // 向服务器端发送信息的DataOutputStream  
                OutputStream out = socket.getOutputStream();
                // 装饰标准输入流，用于从控制台输入  
                Scanner scanner = new Scanner(System.in);  
                //while (true) {  
                	//String send = scanner.nextLine();
                	String send = "weixiao";
                    System.out.println("客户端：" + send);  
                    byte[] by = send.getBytes("UTF-8");  
                    Header header = new Header((byte) 1, (byte) 1, (byte) 1, (byte) 1, (byte) 1, "713f17ca614361fb257dc6741332caf2", by.length);  
                    Message message = new Message(header, "welcome", send);  
                    out.write(message.toByte());  
                    out.flush();  
                    // 把从控制台得到的信息传送给服务器  
                    // out.writeUTF("客户端：" + send);  
                    // 读取来自服务器的信息  
               // }
            } finally {  
                socket.close();  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
}  
