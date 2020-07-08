package com.xwder.biz.app.modules.video.service.intf;

import java.io.File;

/**
 * video 处理接口
 */
public interface VideoService {

    /**
     * 转换mp4文件格式ts，然后切割hls，然后上传cos
     *
     * @param sourceFile
     * @return
     */
    Object convertAndSpiltAndUpCos(File sourceFile);

    /**
     * 直接切割mp4切割hls，然后上传cos
     *
     * @param sourceFile
     * @return
     */
    String spiltAndUpCos(File sourceFile, String m3u8Name, int splitTime, String uploadPath, String bucketName, String region);
}
