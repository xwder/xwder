package com.xwder.biz.app.modules.video.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.xwder.biz.app.attribute.SysConfigAttribute;
import com.xwder.biz.app.modules.video.service.intf.VideoService;
import com.xwder.cloud.commmon.api.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@RequestMapping(value = "/video")
@Controller
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private VideoService videoService;


    @PostMapping("/up")
    public CommonResult uploadFile(@RequestParam("videoFile") MultipartFile videoFile, @RequestParam("m3u8Name") String m3u8Name,
                                   @RequestParam("uploadPath") String uploadPath,
                                   @RequestParam(value = "splitTime", required = false) Integer splitTime,
                                   @RequestParam(value = "bucketName", required = false) String bucketName,
                                   @RequestParam(value = "region", required = false) String region,
                                   HttpServletRequest request) {
        CommonResult commonResult;
        //判断文件是否为空
        if (videoFile.isEmpty()) {
            commonResult = CommonResult.failed("文件不能为空");
            logger.info("文件为空");
            return commonResult;
        }
        String originalFilename = videoFile.getOriginalFilename();
        String[] splitNames = originalFilename.split("\\.");
        String fileNameSuffix = "";
        if (splitNames.length > 0) {
            fileNameSuffix = splitNames[splitNames.length - 1];
        } else {
            commonResult = CommonResult.failed("文件后缀不能空格式未知");
            logger.info("文件后缀不能空格式未知");
            return commonResult;
        }

        splitTime = splitTime == null ? sysConfigAttribute.getVideoSplitTime() : splitTime;
        bucketName = bucketName == null ? sysConfigAttribute.getTencentCosBucketName() : bucketName;
        region = region == null ? sysConfigAttribute.getTencentCosRegion() : region;

        // 保存文件的路径
        String videoUploadSaveDir = sysConfigAttribute.getVideoUploadSaveDir() + File.separator + uploadPath;
        File videoUploadSaveDirFile = new File(videoUploadSaveDir);
        if (videoUploadSaveDirFile.exists()) {
            String msg = originalFilename + " 文件" + m3u8Name + " 已存在";
            commonResult = CommonResult.failed(msg);
            logger.info(msg);
            return commonResult;
        }
        // 保存到指定目录
        FileUtil.mkdir(videoUploadSaveDirFile);

        String saveFilePath = videoUploadSaveDirFile.getAbsolutePath() + File.separator + m3u8Name + "." + fileNameSuffix;
        File saveFile = new File(saveFilePath);

        try {
            videoFile.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("保存文件失败 {}",e);
        }

        String m3u8Url = videoService.spiltAndUpCos(saveFile, m3u8Name, splitTime, uploadPath, bucketName, region);
        String completeM3u8Url = sysConfigAttribute.getTencentCdnPreFixUrl() + "/" + m3u8Url;
        commonResult = CommonResult.success(completeM3u8Url);
        logger.info("上传成功 访问url[{}]",completeM3u8Url);
        return commonResult;
    }
}
