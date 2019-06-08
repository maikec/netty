package nio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

/**
 * 说明
 *  @author maikec
 *  @date 2019/6/7
 */
public class NIOTimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length >0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        MultiTimeServer multiTimeServer = new MultiTimeServer(port);
        new Thread(multiTimeServer,"NIO-01").start();
    }
}
