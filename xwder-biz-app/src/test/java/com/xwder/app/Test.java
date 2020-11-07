package com.xwder.app;

import cn.hutool.core.io.FileUtil;
import com.google.common.base.Joiner;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/21
 */
public class Test {

    @org.junit.Test
    public void txt() {
        File file = new File("D:\\bookchapter-autopartition-long.txt");
        int i = 1;
        for (; i < 97; i++) {
            if (i == 1) {
                String s[] = {"1-1000=1"};
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            } else {
                String s = ((i - 1) * 1000 + 1) + "-" + (i * 1000) + "=" + i;
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            }
            i = i++;
        }
    }

    /**
     * 测试guava joiner
     */
    @org.junit.Test
    public void joinerTest(){
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        String join = Joiner.on("-").join(list);
        System.out.println("join: " + join);
    }

    @org.junit.Test
    public void urlEncode(){
        try {
            System.out.println(URLEncoder.encode("http://9di2ey.natappfree.cc/login/qq/getauthcode","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
