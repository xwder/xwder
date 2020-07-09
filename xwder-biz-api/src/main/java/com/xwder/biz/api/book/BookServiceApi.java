//package com.xwder.biz.api.book;
//
//import feign.Client;
//import feign.Contract;
//import feign.Feign;
//import feign.Logger;
//import feign.codec.Decoder;
//import feign.codec.Encoder;
//import feign.slf4j.Slf4jLogger;
//import lombok.Data;
//import org.springframework.cloud.openfeign.FeignClientsConfiguration;
//import org.springframework.context.annotation.Import;
//import org.springframework.stereotype.Component;
//
///**
// * @author wande
// * @version 1.0
// * @date 2019/12/27
// */
//@Data
//@Component
//@Import(FeignClientsConfiguration.class)
//public class BookServiceApi {
//
//    private BookInfoServiceApi bookInfoServiceApi;
//    private ChapterServiceApi chapterServiceApi;
//
//    public BookServiceApi(Decoder decoder, Encoder encoder, Client client, Contract contract) {
//
//        this.bookInfoServiceApi = Feign.builder().client(client)
//                .encoder(encoder)
//                .decoder(decoder)
//                .contract(contract)
//                .logger(new Slf4jLogger(BookInfoServiceApi.class))
//                .logLevel(Logger.Level.FULL)
//                .target(BookInfoServiceApi.class, "http://xwder-biz-book");
//
//        this.chapterServiceApi = Feign.builder().client(client)
//                .encoder(encoder)
//                .decoder(decoder)
//                .contract(contract)
//                .logger(new Slf4jLogger(ChapterServiceApi.class))
//                .logLevel(Logger.Level.FULL)
//                .target(ChapterServiceApi.class, "http://xwder-biz-book");
//
//    }
//}
