package org.lhx.nio;

import java.nio.ByteBuffer;

/**
 * @author lhx
 * @date 2019/8/16 - 9:11
 */
public class TestBuffer {

    public static void test1() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.isDirect());
    }

    public static void test() {
        String str = "abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put(str.getBytes());
        byteBuffer.flip();
        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst, 0, 2);
        System.out.println(new String(dst, 0, 2));
        System.out.println(byteBuffer.position());

        //mark标记
        byteBuffer.mark();
        byteBuffer.get(dst, 2, 2);
        System.out.println(new String(dst, 2, 2));
        System.out.println(byteBuffer.position());
        //恢复到mark位置
        byteBuffer.reset();
        System.out.println(byteBuffer.position());
        if (byteBuffer.hasRemaining()) {
            System.out.println(byteBuffer.remaining());
        }
    }

    public static void main(String[] args) {
        test1();
        test();
        //分配指定大小的缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        System.out.println("----------------allocate()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //存入数据到缓冲区
        String str = "abcde";
        byteBuffer.put(str.getBytes());
        System.out.println("----------------put()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //切换读取数据模式
        byteBuffer.flip();
        System.out.println("----------------filp()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //利用get()读取缓冲区的数据
        byte[] dst = new byte[byteBuffer.limit()];
        byteBuffer.get(dst);
        System.out.println(new String(dst, 0, dst.length));
        System.out.println("----------------get()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //可重复读数据
        byteBuffer.rewind();
        System.out.println("----------------rewind()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        //清空缓冲区
        byteBuffer.clear();
        System.out.println("----------------clear()------------------");
        System.out.println(byteBuffer.position());
        System.out.println(byteBuffer.limit());
        System.out.println(byteBuffer.capacity());

        System.out.println(byteBuffer.get());

    }
}
