/**
 * 公司名称
 * <p>
 * 本源代码由《netty》及其作者共同所有，未经版权持有者的事先书面授权，
 * 不得使用、复制、修改、合并、发布、分发和/或销售本源代码的副本。
 *
 * @copyright Copyright (c) 2019-2019+3. （company）all rights reserved.
 */
/**
 * 非阻塞IO
 * 于jdk1.4版本新增，是对BIO的补充。支持阻塞和非阻塞两种模式
 *
 * 基本组件
 * 1.Buffer： 在NIO中所有数据都是通过缓冲区处理的（数据的读写）。缓冲区实质是一个数组（通常是字节数组ByteBuffer）
 * 2.Channel：网络数据通过Channel来读写，Channel是全双工的，而流是单向传输的
 * 3.多路复用器Selector: Selector是java nio的基础。selector会不断轮询注册在其上的Channel,如果channel发生读写那么就会被轮询出来，
 *   然后通过key获取对应的channel集合再做进一步的处理
 */
package nio;