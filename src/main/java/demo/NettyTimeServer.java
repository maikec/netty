package demo;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * netty入门server
 *  @author maikec
 *  @date 2019/6/8
 */
public class NettyTimeServer {
    public static void main(String[] args) {
        int port = 8080;
        if (null != args && args.length>0){
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        new NettyTimeServer().bind(port);
    }

    private void bind(int port) {

        //线程组
        try(NioEventLoopGroup bossGroup = new NioEventLoopGroup();
            NioEventLoopGroup workGroup = new NioEventLoopGroup();) {
            final ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    //The {@link Class} which is used to create {@link Channel} instances from
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,1024)
                    //Set the {@link ChannelHandler} which is used to serve the request for the {@link Channel}'s
                    .childHandler(new ChildHandler());
            //绑定端口
            final ChannelFuture channelFuture = serverBootstrap.bind(port);
            System.out.println("Server is started on " + port);
            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class ChildHandler extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline().addLast(new NettyTimeHandler());
        }
    }
}
