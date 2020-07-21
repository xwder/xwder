package com.xwder.app;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.util.Arrays;

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
                String s[] = {"1-1000"};
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            } else {
                String s = ((i - 1) * 1000 + 1) + "-" + (i * 1000);
                FileUtil.appendLines(Arrays.asList(s), file, "Utf-8");
            }
            i = i++;
        }
    }

}
