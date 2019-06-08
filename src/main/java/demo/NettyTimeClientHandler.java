package demo;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

import java.util.logging.Logger;


/**
 * 说明
 *  @author maikec
 *  @date 2019/6/8
 */
public class NettyTimeClientHandler extends ChannelHandlerAdapter {
    private static Logger logger = Logger.getLogger(NettyTimeClientHandler.class.getName());
    private final ByteBuf byteBuf;
    public NettyTimeClientHandler(){
        final byte[] bytes = "Query Time Order".getBytes();
        byteBuf = Unpooled.buffer(bytes.length);
        byteBuf.writeBytes(bytes);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        final byte[] req = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(req);
        final String body = new String(req, CharsetUtil.UTF_8);
        System.out.println("Now is " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warning("Exception "+cause.getMessage());
        ctx.close();
    }
}
