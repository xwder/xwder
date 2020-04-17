package com.xwder.cloud.commmon.utils;

import java.util.Properties;

/**
 * 关于操作系统的工具类
 *
 * @author wande
 * @version 1.0
 * @date 2020/04/17
 */
public class SystemUtil {

    /**
     * 判断系统是Linux 还是 windows
     *
     * @return
     */
    public static boolean isOSLinux() {
        Properties prop = System.getProperties();

        String os = prop.getProperty("os.name");
        if (os != null && os.toLowerCase().indexOf("linux") > -1) {
            return true;
        } else {
            return false;
        }
    }
}
