package com.xwder.cloud.commmon.constan;

/**
 * 网关地址信息
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/30
 */
public class GateWayConstant {

    /**
     * 默认网关地址
     */
    public static final String GATEWAY_URL = "https://gateway.xwder.com";

    /**
     * XWDER-BIZ-BOOK 服务本地地址
     */
    public static final String XWDER_BIZ_BOOK_URL = "http://localhost:9001";

    // book微服务访问地址 开发本地使用
    public static final String SERVICE_BOOK_GATEWAY_URL = "http://localhost:9001";
    // book微服务访问地址 通过网关访问
    //public static final String SERVICE_BOOK_GATEWAY_URL = GATEWAY_URL + "/" + ServiceConstant.XWDER_BIZ_BOOK ;
}
