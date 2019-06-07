package bio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 客户端
 *  @author maikec
 *  @date 2019/6/7
 */
public class TimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        BufferedReader reader = null;
        PrintWriter out = null;
        try (Socket socket = new Socket("127.0.0.1", port)) {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(),true);

            out.println("Query Time Order");
            System.out.println("Send Order 2 server success");
            String resp = reader.readLine();
            System.out.println("Info from server" + resp);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null != reader){
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (null != out){
                out.close();
//                out = null;
            }
        }
    }
}
