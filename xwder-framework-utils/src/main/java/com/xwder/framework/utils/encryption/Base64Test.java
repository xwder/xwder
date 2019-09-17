package com.xwder.framework.utils.encryption;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * @Author: xwder
 * @Date: 2019/8/26 22:27
 * @Description:
 */
public class Base64Test {

    public static void main(String[] args) {
        byte[] b = new byte[0];
        try {
            b = ("eureka" + ":" + "123456").getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String basicString = new BASE64Encoder().encode(b);
        System.out.println(basicString);
    }
}
