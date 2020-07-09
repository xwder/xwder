package com.xwder.manage.modules.m3u8;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 */
public class M3u8DownLoadService {

    public static void main(String[] args) {
        File file = new File(("C:\\Users\\xwder\\Desktop\\新建文件夹\\index.m3u8"));
        M3u8DownLoadService m3u8DownLoadService = new M3u8DownLoadService();
        List list = m3u8DownLoadService.readFile2List(file);
        System.out.println(list);
    }


    /**
     * 读取文件数据
     *
     * @param file
     * @return
     */
    private List readFile2List(File file) {
        List dataList = Lists.newArrayList();
        try {
            //构造一个BufferedReader类来读取文件
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            int lineNo = 0;
            String s = null;
            //使用readLine方法，一次读一行
            while ((s = br.readLine()) != null) {
                if (lineNo > 5 && lineNo % 2 == 0) {
                    dataList.add(s);
                }
                lineNo++;
            }
            br.close();
        } catch (Exception e) {
            return Lists.newArrayList();
        }

        return dataList;
    }
}
