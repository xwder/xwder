package com.xwder.app.netty.server;

import com.xwder.app.netty.client.NettyServerIdeTrigger;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author xwder
 * @date 2021/3/30 16:02
 **/
public class ServerInitializer extends ChannelInitializer<SocketChannel> {

    private final Logger logger = LoggerFactory.getLogger(ServerInitializer.class);

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        logger.info("NettyServer ServerInitializer initChannel() ~~");
        ChannelPipeline pipeline = ch.pipeline();
        pipeline
                .addLast(new ChunkedWriteHandler())
                .addLast(new IdleStateHandler(0, 0, 5, TimeUnit.SECONDS))
                .addLast(new NettyServerIdeTrigger())
        ;
    }
}
