# netty
# BIO
 * <b>Acceptor线程会根据客户端的请求0，派生出一个对应的处理线程来做链路处理，然后把处理结果通过流的方式响应给客户端</b>
 * ## BIO特征
 * 一个请求一个业务处理线程
# 非阻塞IO
  * 于jdk1.4版本新增，是对BIO的补充。支持阻塞和非阻塞两种模式
  *
  * 基本组件
  * 1.Buffer： 在NIO中所有数据都是通过缓冲区处理的（数据的读写）。缓冲区实质是一个数组（通常是字节数组ByteBuffer）
  * 2.Channel：网络数据通过Channel来读写，Channel是全双工的，而流是单向传输的
  * 3.多路复用器Selector: Selector是java nio的基础。selector会不断轮询注册在其上的Channel,如果channel发生读写那么就会被轮询出来，
  *   然后通过key获取对应的channel集合再做进一步的处理
# netty
  * NioEventLoopGroup(acceptor,client)
     is used for NIO {@link Selector} based {@link Channel}s.
  * ServerBootstrap
  * NioServerSocketChannel
    uses NIO selector based implementation to accept new connections.
  * ChannelOption
    A {@link ChannelOption} allows to configure a {@link ChannelConfig} in a type-safe way.
  * ChannelFuture
    All I/O operations in Netty are asynchronous
  * ChannelInitializer
    A special {@link ChannelHandler} which offers an easy way to initialize a {@link Channel} once it was
    registered to its {@link EventLoop}
  * ChannelPipeline   
    A list of {@link ChannelHandler}s which handles or intercepts inbound events and outbound operations of a {@link Channel}
    Intercepting Filter
    Each channel has its own pipeline and it is created automatically when a new channel is created
  * ChannelHandler
    Handles an I/O event or intercepts an I/O operation, and forwards it to its next handler in its {@link ChannelPipeline}.  
    -> @see ChannelHandlerAdapter ChannelHandlerContext ChannelPipeline
  * ChannelHandlerContext
    Enables a {@link ChannelHandler} to interact with its {@link ChannelPipeline}and other handlers
  * ByteBuf
    @see Unpooled