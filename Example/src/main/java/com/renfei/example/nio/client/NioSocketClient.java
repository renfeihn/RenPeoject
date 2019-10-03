package com.renfei.example.nio.client;

/**
 * Created by renfei on 2019/6/1.
 */
//客户端client类
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * description:客户端
 *
 * @author renfei
 */
public class NioSocketClient {
    public void start() {
        try (SocketChannel socketChannel = SocketChannel.open()) {
            //连接服务端socket
            SocketAddress socketAddress = new InetSocketAddress("localhost", 8888);
            socketChannel.connect(socketAddress);

            int sendCount = 9;

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            //这里最好使用selector处理   这里只是为了写的简单
            while (sendCount < 10) {
                buffer.clear();
                //向服务端发送消息
                String in = "输入";
                buffer.put(in.getBytes());
                //读取模式
                buffer.flip();
                socketChannel.write(buffer);
                buffer.clear();

                //从服务端读取消息
                int readLenth = socketChannel.read(buffer);
                //读取模式
                buffer.flip();
                byte[] bytes = new byte[readLenth];
                buffer.get(bytes);
                System.out.println(new String(bytes, "UTF-8"));
                buffer.clear();

                sendCount++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//client启动类
/**
 * description:
 *
 * @author renfei
 */
class ClientMain {
    public static void main(String[] args) {
        new NioSocketClient().start();
    }
}
