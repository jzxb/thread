package org.lhx.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * @author lhx
 * @date 2019/8/16 - 14:25
 */
public class TestChannel {

    public static void test1() {
        //利用通道完成文件的复制(非直接缓冲区)。
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.jpg");
            fos = new FileOutputStream("2.jpg");

            //获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配指定大小的缓冲区。
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //将通道中的数据存入缓冲区中。
            while (inChannel.read(buf) != -1) {
                buf.flip();
                //将缓冲区中的数据写入通道。
                outChannel.write(buf);
                buf.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null) {
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null) {
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void test2() throws IOException {
        long start = System.currentTimeMillis();
        //使用直接缓冲区完成文件的复制（内存映射文件）
        FileChannel in = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE_NEW);
        //内存映射文件
        MappedByteBuffer inMappedBuf = in.map(FileChannel.MapMode.READ_ONLY, 0, in.size());
        MappedByteBuffer outMappedBuf = out.map(FileChannel.MapMode.READ_WRITE, 0, in.size());

        //直接对缓冲区进行数据的读写操作
        byte[] dst = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dst);
        outMappedBuf.put(dst);
        in.close();
        out.close();
        long end = System.currentTimeMillis();
        System.out.println("耗时" + (end - start));
    }

    public static void test3() throws IOException {
        //通道之间的数据传输（直接缓冲区）
        FileChannel in = FileChannel.open(Paths.get("1.jpg"), StandardOpenOption.READ);
        FileChannel out = FileChannel.open(Paths.get("3.jpg"), StandardOpenOption.WRITE, StandardOpenOption.READ, StandardOpenOption.CREATE);
//        in.transferTo(0, in.size(), out);
        out.transferFrom(in, 0, in.size());
        in.close();
        out.close();
    }

    public static void test4() throws IOException {
        //分散读取聚集写入
        RandomAccessFile raf1 = new RandomAccessFile("1.txt", "rw");

        //获取通道
        FileChannel fileChannel = raf1.getChannel();
        //分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        fileChannel.read(bufs);

        for (ByteBuffer buf : bufs) {
            buf.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println("----------------------");
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        //聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("2.txt", "rw");
        FileChannel raf2Channel = raf2.getChannel();
        raf2Channel.write(bufs);
        String a = "abc";
        a.length();
    }

    public static void test5() {
        //字符集
        SortedMap<String, Charset> stringCharsetSortedMap = Charset.availableCharsets();
        Set<Map.Entry<String, Charset>> set = stringCharsetSortedMap.entrySet();
        for (Map.Entry<String, Charset> stringCharsetEntry : set) {
            System.out.println(stringCharsetEntry.getKey() + "=" + stringCharsetEntry.getValue());
        }
    }

    public static void test6() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");

        //获取编码器与解码器
        //编码器
        CharsetEncoder charsetEncoder = cs1.newEncoder();
        //解码器
        CharsetDecoder charsetDecoder = cs1.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);
        charBuffer.put("近战小白");
        charBuffer.flip();

        //编码
        ByteBuffer encode = charsetEncoder.encode(charBuffer);
        for (int i = 0; i < 6; i++) {
            System.out.println(encode.get());
        }
        encode.flip();
        CharBuffer decode = charsetDecoder.decode(encode);
        System.out.println(decode.toString());

        Charset cs2 = Charset.forName("UTF-8");
        encode.flip();
        CharBuffer decode1 = cs2.decode(encode);
        System.out.println(decode1.toString());
    }

    public static void main(String[] args) throws CharacterCodingException {
        test6();
    }
}
