package nio.simple;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Description TODO
 * @Date 2021/1/17 18:57
 * @Author zhouyq
 */
public class Client {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9876);

        OutputStream out = socket.getOutputStream();
        out.write("hello nio".getBytes());
        out.flush();

        // 获取服务端响应数据
        InputStream in = socket.getInputStream();
        byte[] resp = new byte[1024];
        in.read(resp);
        System.out.println("服务端响应：" + new String(resp));

        out.close();
        socket.close();

    }
}
