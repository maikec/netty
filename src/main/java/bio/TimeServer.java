package bio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 基于BIO的Socket服务端
 *  @author maikec
 *  @date 2019/6/7
 */
public class TimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        try(ServerSocket server = new ServerSocket(port)) {
            System.out.println("Server stared on " + port);
            Socket socket = null;
            while (true){
                socket =server.accept();
                new Thread(new TimeServerHandler(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
