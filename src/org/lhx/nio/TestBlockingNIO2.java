package org.lhx.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author lhx
 * @date 2019/8/16 - 19:49
 */
public class TestBlockingNIO2 {
    
    @Test
    public void testClint(){
        SocketChannel sChannel = null;
        FileChannel inChannel = null;
        ByteBuffer byteBuffer = null;
        try {
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            inChannel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            byteBuffer = ByteBuffer.allocate(1024);
            while (inChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                sChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            sChannel.shutdownOutput();
            //接收服务端反馈
            int len;
            while ((len = sChannel.read(byteBuffer)) != -1) {
                byteBuffer.flip();
                System.out.println(new String(byteBuffer.array(),0, len));
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inChannel.close();
                sChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
    
    @Test
    public void testServer(){
        ServerSocketChannel ssChannel = null;
        FileChannel fileChannel = null;
        SocketChannel socketChannel = null;
        try {
            ssChannel = ServerSocketChannel.open();
            fileChannel = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            ssChannel.bind(new InetSocketAddress(9898));
            socketChannel = ssChannel.accept();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            while (socketChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
            //反馈给客户端
            byteBuffer.put("服务端接收成功".getBytes());
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
                fileChannel.close();
                ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
