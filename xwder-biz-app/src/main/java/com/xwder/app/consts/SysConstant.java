package com.xwder.app.consts;

/**
 * @author xwder
 * @Description: 系统常量
 * @date 2020/7/13 12:14
 */
public class SysConstant {

    /**
     * session 中存在登录用户的key
     */
    public static final String SESSION_USER = "sessionUser";

    /**
     * redis user session key
     */
    public static final String USER_SESSION_REDIS_KEY = "xwder:user:session";

    /**
     * redis user session key TIME
     */
    public static final int USER_SESSION_REDIS_TIME = 1800;

    /**
     * 排序字符串
     */
    public static final String order_desc = "desc";

    /**
     * 排序字符串
     */
    public static final String order_asc = "asc";

    /**
     * 通用成功标识
     */
    public static final String SUCCESS = "0";

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 评论类型 博客
     */
    public static final int COMMENT_TYPE_BLOG = 1;
}
