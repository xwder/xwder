package com.xwder.app.netty;

import com.xwder.app.netty.server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

/**
 * @author xwder
 * @date 2021/3/30 15:46
 **/
@Component
public class NettyRunner implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        CompletableFuture.runAsync(()->{
            nettyServer.start();
        });
    }
}
