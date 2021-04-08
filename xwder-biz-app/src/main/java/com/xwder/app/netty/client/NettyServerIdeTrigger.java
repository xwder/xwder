package com.xwder.app.netty.client;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author xwder
 * @date 2021/3/30 16:05
 **/
public class NettyServerIdeTrigger extends ChannelInboundHandlerAdapter {

    private final Logger logger = LoggerFactory.getLogger(NettyServerIdeTrigger.class);

    /**
     * 多大连续5次未收到client的ping消息
     */
    private final static int MAX_UN_REC_PING_TIMES = 5;

    /**
     * 连续多少次未收到client的ping消息
     */
    private AtomicInteger unRecPingTimes = new AtomicInteger();

    /**
     * 客户端发送给服务器端的心跳开始字符串 心跳字符串：XWDER-CLIENT#yyyyMMddHHmmssSSS
     */
    private final String CLIENT_PING_STR_BEGIN = "XWDER-CLIENT#";
    /**
     * 服务器端发送给客户端的心跳开始字符串 心跳字符串：XWDER-SERVER#yyyyMMddHHmmssSSS
     */
    private final String SERVER_PONG_STR_BEGIN = "XWDER-SERVER#";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("客户端:[{}]连接成功", inSocket.getAddress());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("客户端:[{}]连接关闭", inSocket.getAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.error("客户端:[{}]连接异常! ", inSocket.getAddress(), cause);
        ctx.channel().close();
        ctx.fireExceptionCaught(cause);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        unRecPingTimes.set(0);
        if (msg instanceof ByteBuf) {
            ByteBuf ByteBuf = (ByteBuf) msg;
            int len = 50;
            if (ByteBuf.readableBytes() < len) {
                len = ByteBuf.readableBytes();
            }
            byte[] pingBytes = new byte[len];
            ByteBuf.getBytes(0, pingBytes, 0, len);
            String pingStr = new String(pingBytes);
            if (StrUtil.startWith(pingStr, CLIENT_PING_STR_BEGIN)) {
                String pongStr = SERVER_PONG_STR_BEGIN + DateUtil.format(new Date(), DatePattern.PURE_DATETIME_MS_PATTERN);
                byte[] pongBytes = pongStr.getBytes();
                ByteBuf pongBuf = Unpooled.buffer(pongBytes.length);
                pongBuf.writeBytes(pongBytes);
                ctx.channel().writeAndFlush(pongBuf);
                ByteBuf.release();
                logger.info("收到客户端心跳包:[{}],回应心跳包:[{}]", pingStr.trim(), pongStr);
                return;
            }
            ByteBuf newBuf = Unpooled.copiedBuffer(ByteBuf);
            ByteBuf.release();
            ctx.fireChannelRead(newBuf);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("客户端:[{}] channelReadComplete()", inSocket.getAddress());
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
                    break;
            }
            if (event.state().equals(IdleState.ALL_IDLE)) {
                // 失败计数器次数大于等于5次的时候，关闭链接，等待client重连
                if (unRecPingTimes.get() >= MAX_UN_REC_PING_TIMES) {
                    // 连续超过N次未收到client的ping消息，那么关闭该通道，等待client重连
                    ctx.channel().close();
                    logger.info("服务器端连续[{}]次未收到客户端:[{}]消息,关闭通道,等待重连!", unRecPingTimes.get(), inSocket.getAddress());
                } else {
                    // 失败计数器加1
                    logger.info("服务器第[{}]次未收到客户端:[{}]消息! ping超时[{}] ~~", unRecPingTimes.get() + 1, inSocket.getAddress(), eventType);
                    unRecPingTimes.addAndGet(1);
                }
            }
        }

        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inSocket = (InetSocketAddress) ctx.channel().remoteAddress();
        logger.info("客户端:[{}] handlerRemoved()", inSocket.getAddress());
        super.handlerRemoved(ctx);
    }


}
