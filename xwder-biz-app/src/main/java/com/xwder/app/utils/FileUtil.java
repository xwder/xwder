package com.xwder.app.utils;

import org.springframework.util.ResourceUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件下载工具类
 *
 * @Author: xwder
 * @Description:
 * @Date: 2020/12/23 22 47
 */
public class FileUtil {
    /**
     * 文件下载
     *
     * @param response
     * @param fileName
     * @param filePath
     * @return
     */
    public static String downloadFile(HttpServletResponse response, String fileName, String filePath) {
        File path = null;
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
        byte[] buff = new byte[1024];
        BufferedInputStream bis = null;
        OutputStream os = null;
        try {
            path = new File(ResourceUtils.getURL("classpath:").getPath());
            os = response.getOutputStream();
            bis = new BufferedInputStream(new FileInputStream(new File(filePath)));
            int i = bis.read(buff);
            while (i != -1) {
                os.write(buff, 0, buff.length);
                os.flush();
                i = bis.read(buff);
            }
        } catch (FileNotFoundException e1) {
            return "系统找不到指定的文件";
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "success";
    }
}
