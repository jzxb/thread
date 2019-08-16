package org.lhx.nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @author lhx
 * @date 2019/8/16 - 21:08
 */
public class TestPipe {
    public static void main(String[] args) throws IOException {
        Pipe pipe = Pipe.open();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        Pipe.SinkChannel sinkChannel = pipe.sink();
        byteBuffer.put("通过单向管道传输".getBytes());
        byteBuffer.flip();
        sinkChannel.write(byteBuffer);

        Pipe.SourceChannel source = pipe.source();
        byteBuffer.flip();
        int len = source.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(), 0, len));
        source.close();
        sinkChannel.close();
    }
}
