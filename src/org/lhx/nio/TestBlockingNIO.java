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
 * @date 2019/8/16 - 19:22
 */
public class TestBlockingNIO {
    @Test
    public void testClint(){
        SocketChannel sChannel = null;
        FileChannel inChinnel = null;
        try {
            //客户端
            //获取通道
            sChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9898));
            inChinnel = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
            //分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //读取本地文件并发送到服务端
            while (inChinnel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                sChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭通道
            try {
                inChinnel.close();
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
        SocketChannel sChannel = null;
        try {
            //服务端
            //获取通道
            ssChannel = ServerSocketChannel.open();
            fileChannel = FileChannel.open(Paths.get("2.jpg"), StandardOpenOption.WRITE, StandardOpenOption.CREATE_NEW);
            //绑定连接端口号
            ssChannel.bind(new InetSocketAddress(9898));
            //获取客户端连接
            sChannel = ssChannel.accept();

            //分配指定大小的缓冲区
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

            //接收客户端的数据并保存在本地。
            while (sChannel.read(byteBuffer) != -1) {
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
                byteBuffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭通道
                sChannel.close();
                fileChannel.close();
                ssChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



    }
}
