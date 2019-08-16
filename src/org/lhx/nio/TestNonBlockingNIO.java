package org.lhx.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;
import java.util.Iterator;

/**
 * @author lhx
 * @date 2019/8/16 - 20:16
 */
public class TestNonBlockingNIO {
    @Test
    public void testclint() throws IOException {
        //获取通道
        SocketChannel sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
        //切换成非阻塞模式
        sChannel.configureBlocking(false);
        //分配缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        //发送数据给服务端
        byteBuffer.put(new Date().toString().getBytes());
        byteBuffer.flip();
        sChannel.write(byteBuffer);
        byteBuffer.clear();

        sChannel.close();
    }
    
    @Test
    public void testServer() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9898));

        //获取选择器
        Selector selector = Selector.open();
        //将通道注册到选择器上,并且指定“监听接收事件”
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //轮询式的获取选择器上已经“准备就绪”的事件
        while (selector.select() > 0) {
            //获取当前选择器中所有注册的选择键（已就绪的监听事件）
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            //迭代获取
            while (it.hasNext()) {
                //获取准备就绪的事件
                SelectionKey selectionKey = it.next();
                //判断具体是什么事件准备就绪
                if (selectionKey.isAcceptable()) {
                    //若接收就绪，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //切换非阻塞模式
                    socketChannel.configureBlocking(false);

                    //将改通道注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (selectionKey.isReadable()) {
                    //获取当前选择器上读就绪状态的通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    //读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        byteBuffer.clear();
                    }
                }
                //取消选择键
                it.remove();
            }
        }
    }
}
