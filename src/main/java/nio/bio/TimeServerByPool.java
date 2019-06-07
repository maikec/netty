package nio.bio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.concurrent.*;

/**
 * 基于BIO的Socket服务端的工作线程池实现
 *  @author maikec
 *  @date 2019/6/7
 */
public class TimeServerByPool {
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
//            final ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors()
//                    ,10,3000, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

            ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("TimeServerByPool-%d").build();
            final ExecutorService executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                    10,3000,TimeUnit.SECONDS,new LinkedBlockingQueue<>(),threadFactory);
            Socket socket = null;
            while (true){
                socket =server.accept();
                executorService.submit(new TimeServerHandler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
