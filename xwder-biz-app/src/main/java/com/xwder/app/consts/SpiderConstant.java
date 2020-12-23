package com.xwder.app.consts;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xwder
 * @Description: 爬虫类常量
 * @date 2020/7/16 21:35
 */
public class SpiderConstant {

    /**
     * shuquge.com headers
     */
    public static final Map<String, String> SQG_SPIDER_HEADER_MAP;

    static {
        SQG_SPIDER_HEADER_MAP = new HashMap<String, String>();
        SQG_SPIDER_HEADER_MAP.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        SQG_SPIDER_HEADER_MAP.put("Accept-Language", "zh-cn,zh;q=0.5");
        SQG_SPIDER_HEADER_MAP.put("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7");
        SQG_SPIDER_HEADER_MAP.put("Connection", "keep-alive");
        //header.put("cookie", "acw_tc=2760820d15766480375335721edd64371fcb400d47561f8896c6e28c7d3b40; uuid_tt_dd=10_9926153920-1576648097988-182265; dc_session_id=10_1576648097988.363322; Hm_lvt_6bcd52f51e9b3dce32bec4a3997715ac=1576644694,1576648099; Hm_ct_6bcd52f51e9b3dce32bec4a3997715ac=6525*1*10_9926153920-1576648097988-182265; announcement=%257B%2522isLogin%2522%253Afalse%252C%2522announcementUrl%2522%253A%2522https%253A%252F%252Fblogdev.blog.csdn.net%252Farticle%252Fdetails%252F103053996%2522%252C%2522announcementCount%2522%253A0%252C%2522announcementExpire%2522%253A3600000%257D; dc_tos=q2p1ir; Hm_lpvt_6bcd52f51e9b3dce32bec4a3997715ac=1576648180; c-login-auto=3; acw_sc__v2=5df9bf415fd91f48063efe6c7f23be98ca36f37c");
        SQG_SPIDER_HEADER_MAP.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36");
        //SPIDER_HEADER_MAP.put("referer", sourceUrl);
    }
}
