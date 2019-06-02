package com.renfei.example.nio.server;

/**
 * Created by renfei on 2019/6/1.
 */
//服务端server类
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * description:
 *
 * @author renfei
 */
public class NioSocketServer {

    private volatile byte flag = 1;
    private final static int SERVER_PORT = 8888;

    public void setFlag(byte flag) {
        this.flag = flag;
    }

    public void start() {
        //创建serverSocketChannel，监听8888端口
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
            serverSocketChannel.socket().bind(new InetSocketAddress(SERVER_PORT));
            //设置为非阻塞模式
            serverSocketChannel.configureBlocking(false);
            //为serverChannel注册selector
            Selector selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("服务端开始工作：");

            //创建消息处理器
            ServerHandlerBs handler = new ServerHandlerImpl(1024);

            while (flag == 1) {
                selector.select();
                System.out.println("开始处理请求 ： ");
                //获取selectionKeys并处理
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    try {
                        //连接请求
                        if (key.isAcceptable()) {
                            System.out.println("请求连接");
                            handler.accept(key);
                        }

                        if (key.isWritable()) {
                            // 开始写
                            handler.write(key, "收到处理完成！");
                        }

                        //读请求
                        if (key.isReadable()) {
                            System.out.print("请求读 ：");
                            String readStr = handler.read(key);
                            System.out.println(readStr);
                            // 处理逻辑
                        }

//                        if(key.isConnectable()){
//                            handler.close(key);
//                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //处理完后移除当前使用的key
                    keyIterator.remove();
                }
                System.out.println("完成请求处理。");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//server端启动类

/**
 * description:
 *
 * @author refei
 */
class ServerMain {
    public static void main(String[] args) {
        NioSocketServer server = new NioSocketServer();
        new Thread(() -> {
            try {
                Thread.sleep(10 * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                server.setFlag((byte) 0);
            }
        }).start();
        server.start();
    }
}
