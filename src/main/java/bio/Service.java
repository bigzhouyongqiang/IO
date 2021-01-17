package bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  BIO：
 *      缺点：每有一个客户端请求都要分配一个线程处理，大量线程之间的上下文切换效率低，并发能力弱
 *      优点：代码简单、开发简单不容易出错
 *
 * @Date 2021/1/17 16:57
 * @Author zhouyq
 */
public class Service {

    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("0.0.0.0", 9876));

        while (true) {

            // accept方法是阻塞方法
            print("获取客户端连接（阻塞）");
            Socket socket = serverSocket.accept();
            print("服务端与客户端连接创建成功", socket.getRemoteSocketAddress().toString());

            new Thread(()->{
                try {

                    // getInputStream方法是阻塞方法
                    print("获取输入流（阻塞）");
                    InputStream inputStream =  socket.getInputStream();

                    byte[] req = new byte[1024];
                    inputStream.read(req);
                    print("客户端请求数据", new String(req));

                    socket.getOutputStream().write("hello client!".getBytes());
                    socket.getOutputStream().flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println();
                    print("客户端处理完成，关闭socket", socket.getRemoteSocketAddress().toString());
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }).start();

        }

    }

    static void print(String desc) {
        System.out.println(desc);
    }

    static void print(String desc, String str) {
        System.out.println(desc + "===========>" + str);
    }

}
