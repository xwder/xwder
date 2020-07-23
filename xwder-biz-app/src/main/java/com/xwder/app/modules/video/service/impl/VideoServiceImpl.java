package com.xwder.biz.app.modules.video.service.impl;

import com.github.pagehelper.util.StringUtil;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import com.xwder.app.modules.video.service.intf.VideoService;
import com.xwder.biz.app.modules.video.ffmpeg.VideoOperateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

/**
 * video 处理视频 上传 cos
 *
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@Service
public class VideoServiceImpl implements VideoService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String serviceName = "video service 处理视频上传cos";

    @Autowired
    private TencentCosService tencentCosService;

    @Autowired
    private VideoOperateService videoOperateService;

    /**
     * 转换mp4文件格式ts，然后切割hls，然后上传cos
     *
     * @param sourceFile
     * @return
     */
    @Override
    public Object convertAndSpiltAndUpCos(File sourceFile) {
        String convertFileName = "";
        String m3u8Name = "";
        int splitTime = 5;
        String uploadPath = "";
        String bucketName = "";
        String regionName = "";
        String newFilePath = videoOperateService.convertAndSplitVideoHls(sourceFile, convertFileName, m3u8Name, splitTime);
        if (newFilePath == null) {
            logger.error("[{}]转换切割文件失败", serviceName);
            return null;
        }

        File newFile = new File(newFilePath);
        // 遍历分割的目录 上传文件到cos
        File parentFile = newFile.getParentFile();
        File[] files = parentFile.listFiles();

        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                long diff = file1.lastModified() - file2.lastModified();
                if (diff > 0) {
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        fileList.forEach(file -> System.out.println(file.getPath()));
        fileList.remove(newFile);
        String m3u8UploadPath = null;
        for (File file : fileList) {
            String completeUploadPath = uploadPath + "/" + file.getName();
            String uploadResult = tencentCosService.uploadTenCos(file, completeUploadPath, bucketName, regionName);
            String fileName = file.getName();
            if (fileName.endsWith("m3u8")) {
                m3u8UploadPath = completeUploadPath;
            }
        }
        return m3u8UploadPath;
    }

    /**
     * 直接切割mp4切割hls，然后上传cos
     *
     * @param sourceFile
     * @return
     */
    @Override
    public String spiltAndUpCos(File sourceFile, String m3u8Name, int splitTime, String uploadPath, String bucketName, String region) {
        String m3u8FilePath = videoOperateService.splitMp4VideoHls(sourceFile, m3u8Name, splitTime);
        if (StringUtil.isEmpty(m3u8FilePath) && m3u8FilePath != null ) {
            logger.error("[{}] 直接切割mp4切割hls失败", serviceName);
            return null;
        }
        File m3u8File = new File(m3u8FilePath);
        String m3ubFileUrl = uploadFileToCos(m3u8File, uploadPath, bucketName, region);
        return m3ubFileUrl;
    }

    /**
     * 上传指定目录的文件到cos
     *
     * @param path
     * @param uploadPath
     * @param bucketName
     * @param region
     * @return m3u8文件cdn后缀地址
     */
    private String uploadFileToCos(File path, String uploadPath, String bucketName, String region) {
        File[] files = path.getParentFile().listFiles();
        List<File> fileList = new ArrayList<>(Arrays.asList(files));
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File file1, File file2) {
                long diff = file1.lastModified() - file2.lastModified();
                if (diff > 0) {
                    return 1;
                } else if (diff == 0) {
                    return 0;
                } else {
                    return -1;
                }
            }
        });
        fileList.forEach(file -> System.out.println(file.getPath()));
        String m3ubFileUrl = null;
        for (File file : fileList) {
            String completeUploadPath = uploadPath + "/" + file.getName();
            String uploadResult = tencentCosService.uploadTenCos(file, completeUploadPath, bucketName, region);
            String fileName = file.getName();
            if (fileName.endsWith("m3u8")) {
                m3ubFileUrl = completeUploadPath;
            }
            System.out.println(file.getName() + " -  " + uploadResult);
        }

        return m3ubFileUrl;
    }
}
