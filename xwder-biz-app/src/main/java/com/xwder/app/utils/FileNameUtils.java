package com.xwder.app.utils;

import cn.hutool.core.util.RandomUtil;

import java.util.Date;

/**
 * 生成随机文件名
 *
 * @author wande
 * @version 1.0
 * @date 2020/08/31
 */
public class FileNameUtils {

    /**
     * 随机文件名 yyyyMMddHHmmssSSS+4位随机数
     *
     * @return
     */
    public static String randomFileNameWithTime() {
        return DateUtil.formatDate(new Date(), DateUtil.format_yyyyMMddHHmmssSSS) + RandomUtil.randomNumbers(4);
    }

}
