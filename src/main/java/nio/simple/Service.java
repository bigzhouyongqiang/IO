package nio.simple;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 *
 *  纯原生非阻塞NIO（没有使用多路复用器）
 *      缺点：如果没有客户端连接或者有很少一部分客户端发送请求，会导致CPU空转，导致资源浪费。
 *           【可以配合多路复用器Selector(select、poll、epoll的封装)解决该缺点】
 *
 *      优点：一个线程就可以处理所有客户端请求
 *           【不会针对每个客户端创建一个线程导致线程之间的上下文切换消耗大量CPU资源】
 *
 * @Date 2021/1/17 18:57
 * @Author zhouyq
 */
public class Service {
    public static void main(String[] args) throws Exception{

        ServerSocketChannel server = ServerSocketChannel.open();
        server.configureBlocking(false);
        server.bind(new InetSocketAddress("0.0.0.0", 9876));

        // 客户端列表
        List<SocketChannel> channelList = new ArrayList();

        while (true) {
            SocketChannel client = server.accept();
            if (null == client) {
//                System.out.println("没有连接");
            } else {
                client.configureBlocking(false);
                SocketAddress clientSocketAddress = client.socket().getRemoteSocketAddress();
                System.out.println("client socket address:" + clientSocketAddress);
                channelList.add(client);
            }

            // 处理所有的客户端请求
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4096);
            for (SocketChannel socketChannel : channelList) {
                int num = socketChannel.read(byteBuffer); // >0 0 -1 socketChannel已经设置过configureBlocking为false所以不会阻塞
                if (num > 0) {
                    byteBuffer.flip();
                    byte[] req = new byte[byteBuffer.limit()];
                    byteBuffer.get(req);
                    System.out.println(new String(req));

                    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
                    buffer.put("hello client!".getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                } else {
                    //System.out.println("客户端尚未发送数据");
                }
            }
        }




    }
}
