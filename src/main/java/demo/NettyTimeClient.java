package demo;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 说明
 *  @author maikec
 *  @date 2019/6/8
 */
public class NettyTimeClient {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length>0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new NettyTimeClient().connect("127.0.0.1",port);
    }

    private void connect(String host, int port) {

        try( NioEventLoopGroup loopGroup = new NioEventLoopGroup();) {
            final Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            ch.pipeline().addLast(new NettyTimeClientHandler());
                        }
                    });

            final ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            System.out.println("Client connected on[" + host + "," + port + "]");
            channelFuture.channel().closeFuture().sync();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
