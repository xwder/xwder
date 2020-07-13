package com.xwder.app.consts;

/**
 * redis key
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/02
 */
public class RedisConstant {

    public static final String cache_pre_key = "xwder:";

    /**
     * 百度 ocr 缓存key 25分钟
     */
    public static final String BAIDU_ACCESS_TOKEN_OCR = "xwder:baidu:ocr-token";
    /**
     * 百度 ocr 缓存时间
     */
    public static final Integer BAIDU_ACCESS_TOKEN_OCR_CACHETIME = 1500;

    /**
     * 腾讯cos临时 缓存时间 25分钟
     */
    public static final Integer TENCENT_COS_TMP_CACHETIME = 1500;

    /**
     * 腾讯cos临时 SECRET_ID
     */
    public static final String TENCENT_COS_TMP_SECRET_ID = "xwder:tencent:cos-tmp-secretid";
    /**
     * 腾讯cos临时 SECRET_KEY
     */
    public static final String TENCENT_COS_TMP_SECRET_KEY = "xwder:tencent:cos-tmp-secretkey";
    /**
     * 腾讯cos临时 SESSIONTOKEN
     */
    public static final String TENCENT_COS_TMP_SESSIONTOKEN = "xwder:tencent:cos-tmp-sessiontoken";

    /**
     * 邮箱校验key 30分钟有效期
     */
    public static final String EMAIL_VERIFY_KEY = "xwder:email:verify-key";

    /**
     * 邮箱校验key 缓存时间 30分钟
     */
    public static final Integer EMAIL_VERIFY_KEY_CACHETIME = 1500;

}
