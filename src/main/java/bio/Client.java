package bio;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Date 2021/1/17 16:56
 * @Author zhouyq
 */
public class Client {

    public static void main(String[] args) throws Exception{

        Socket socket = new Socket("127.0.0.1", 9876);
        OutputStream out = socket.getOutputStream();

        // 客户端向服务端发起请求
        out.write("hello bio".getBytes());
        out.flush();

        // 获取服务端响应数据
        InputStream inputStream = socket.getInputStream();
        byte[] resp = new byte[1024];
        inputStream.read(resp);
        System.out.println("服务端响应：" + new String(resp));

    }
}
