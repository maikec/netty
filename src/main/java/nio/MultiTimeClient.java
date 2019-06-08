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
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 说明
 *
 * @author maikec
 * @date 2019/6/7
 */
public class MultiTimeClient implements Runnable {
    private String host ;
    private int port;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile boolean stop;

    public MultiTimeClient(String host, int port) {
        this.host = host;
        this.port = port;
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (!stop){
            try {
                selector.select(1000);
                final Set<SelectionKey> selectionKeys = selector.selectedKeys();
                final Iterator<SelectionKey> iterator = selectionKeys.iterator();

                SelectionKey selectionKey;
                while (iterator.hasNext()){
                    selectionKey = iterator.next();
                    iterator.remove();

                    try {
                        handleInput(selectionKey);
                    } catch (IOException e){
                        if (selectionKey != null){
                            selectionKey.cancel();
                            if (selectionKey.channel() != null){
                                selectionKey.channel().close();
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
            final SocketChannel socketChannel = (SocketChannel) key.channel();
            if (key.isConnectable()){
                if (socketChannel.finishConnect()){
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    doWrite(socketChannel);
                } else{
                    //连接失败退出进程
                    System.exit(1);
                }
            }
            if (key.isReadable()){
                final ByteBuffer buffer = ByteBuffer.allocate(1024);
                final int byteSize = socketChannel.read(buffer);
                if (byteSize>0){
                    buffer.flip();
                    final byte[] bytes = new byte[buffer.remaining()];
                    buffer.get(bytes);
                    final String body = new String(bytes, CharsetUtil.UTF_8);
                    System.out.println("Now is " + body);
                    this.stop = true;
                } else if (byteSize<0){
                    key.cancel();
                    socketChannel.close();
                }
            }
        }
    }

    private void doWrite(SocketChannel socketChannel) throws IOException {
        final byte[] req = "Query Time Order".getBytes();
        final ByteBuffer buffer = ByteBuffer.allocate(req.length);
        buffer.put(req);
        buffer.flip();
        socketChannel.write(buffer);
        if (!buffer.hasRemaining()){
            System.out.println("Send Order 2 Server succeed");
        }
    }

    private void doConnect() throws IOException{
        if (socketChannel.connect(new InetSocketAddress(host,port))){
            socketChannel.register(selector,SelectionKey.OP_READ);
            doWrite(socketChannel);
        } else {
            socketChannel.register(selector,SelectionKey.OP_CONNECT);
        }
    }
}
