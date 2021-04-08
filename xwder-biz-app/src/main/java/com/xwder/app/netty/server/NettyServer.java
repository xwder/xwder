package com.xwder.app.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xwder
 * @date 2021/3/30 15:47
 **/
@Component
public class NettyServer {

    private final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private final static Integer PORT = 6666;
    private final static int BACKLOG = 128;
    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;
    private EventExecutorGroup EVENT_EXEC = new DefaultEventExecutorGroup(8);
    private ServerBootstrap serverBootstrap;

    public boolean start() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();

        //设置时间循环对象，前者用来处理accept事件，后者用于处理已经建立的连接的io
        serverBootstrap.group(bossGroup, workerGroup)
                //NIO创建的ServerSocketChannel对象用它来建立新accept的连接  Server是NioServerSocketChannel 客户端是NioSocketChannel
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                /*
                 *  第2次握手服务端向客户端发送请求确认，同时把此连接放入队列A中，
                 *  然后客户端接受到服务端返回的请求后，再次向服务端发送请求，表示准备完毕，此时服务端收到请求，把这个连接从队列A移动到队列B中，
                 *  此时A+B的总数，不能超过SO_BACKLOG的数值，满了之后无法建立新的TCP连接,2次握手后和3次握手后的总数
                 *  当服务端从队列B中按照FIFO的原则获取到连接并且建立连接[ServerSocket.accept()]后，B中对应的连接会被移除，这样A+B的数值就会变小
                 *  此参数对于程序的连接数没影响，会影响正在准备建立连接的握手。
                 */
                .option(ChannelOption.SO_BACKLOG, BACKLOG)
                /*
                 *  设置保持活动连接状态
                 *  启用心跳，双方TCP套接字建立连接后（即都进入ESTABLISHED状态），
                 *  并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活，TCP会自动发送一个活动探测数据报文
                 */
                .option(ChannelOption.SO_KEEPALIVE, true)
                /*
                 * TCP协议中，TCP总是希望每次发送的数据足够大，避免网络中充满了小数据块。
                 * Nagle算法就是为了尽可能的发送大数据快。
                 * TCP_NODELAY就是控制是否启用Nagle算法。
                 * 如果要求高实时性，有数据发送时就马上发送，就将该选项设置为true关闭Nagle算法；
                 * 如果要减少发送次数减少网络交互，就设置为false等累积一定大小后再发送。默认为false。
                 */
                .option(ChannelOption.TCP_NODELAY,true)
                // 连接超时30000毫秒
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,30000)
                // 是否允许重复绑定端口，重复启动，会把端口从上一个使用者上抢过来
                .option(ChannelOption.SO_REUSEADDR,true)
                // child是在客户端连接connect之后处理的handler，不带child的是在客户端初始化时需要进行处理的
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ServerInitializer());

        try {
            ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    logger.info("NettyServer 启动成功!");
                }
            });
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            workerGroup = null;
            bossGroup = null;
            logger.error("NettyServer 启动失败 停止!", e);
            workerGroup = null;
            bossGroup = null;
            return false;
        }

        return false;
    }
}
