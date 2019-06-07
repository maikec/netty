package nio.bio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * 处理线程
 *
 * @author maikec
 * @date 2019/6/7
 */
public class TimeServerHandler implements Runnable {
    private Socket socket;
    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        PrintWriter out = null;
        try{
            reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(),true);

            String currentTime = null;
            String body = null;
            while (true){
                body = reader.readLine();
                if (null == body){
                    break;
                }
                System.out.println("Order is" + body);
                currentTime = "Query Time Order".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString()
                        : "Bad Order";
                out.println(currentTime);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
            if (null != this.socket){
                try {
                    this.socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                this.socket = null;
            }
        }

    }
}
