package nio;/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

/**
 * 说明
 *
 * @author maikec
 * @date 2019/6/7
 */
public class MultiTimeServer implements Runnable{
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;
    public MultiTimeServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.bind(new InetSocketAddress(port),1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("MultiTimeServer is stared on" + port);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }
    @Override
    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                   key = iterator.next();
                   iterator.remove();

                   try {
                       handleInput(key);
                   }catch (Exception e){
                       if (null != key){
                           key.cancel();
                           if (key.channel() != null){
                               key.channel().close();
                           }
                       }
                   }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (selector !=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleInput(SelectionKey key) throws IOException{
        if (key.isValid()){
            //处理新接入的请求
            if (key.isAcceptable()){
                //接收新的连接
                final ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                final SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                //绑定新的渠道
                socketChannel.register(selector,SelectionKey.OP_READ);
            }
            if (key.isReadable()){
                final SocketChannel channel = (SocketChannel)key.channel();
                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                final int readByte = channel.read(buffer);
                if (readByte>0){
                    buffer.flip();
                    final byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    final String body = new String(bytes, CharsetUtil.UTF_8);
                    System.out.println("Server receive order" + body);
                    String currentTime = "Query Time Order".equalsIgnoreCase(body)? new Date(System.currentTimeMillis()).toString()
                            : "Bad Order";
                    doWrite(channel,currentTime);
                }else if (readByte<0){
                    key.cancel();
                    channel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel channel, String response) throws IOException {
        if (null != response && response.trim().length()>0){
            final byte[] bytes = response.getBytes();
            final ByteBuffer byteBuffer = ByteBuffer.allocate(bytes.length);
            byteBuffer.put(bytes);
            byteBuffer.flip();
            channel.write(byteBuffer);
        }
    }
}
