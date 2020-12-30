package com.xwder.app.modules.video.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.common.result.CommonResult;
import com.xwder.app.consts.SysConfigConstants;
import com.xwder.app.modules.user.entity.User;
import com.xwder.app.modules.video.service.intf.VideoService;
import com.xwder.app.sysmodules.file.entity.FileUploadBlock;
import com.xwder.app.sysmodules.file.service.intf.FileUploadBlockService;
import com.xwder.app.utils.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.UUID;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@Slf4j
@RequestMapping(value = "/video")
@Controller
public class VideoController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private VideoService videoService;

    @Autowired
    private FileUploadBlockService fileUploadBlockService;

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
            logger.error("保存文件失败 {}", e);
        }

        String m3u8Url = videoService.spiltAndUpCos(saveFile, m3u8Name, splitTime, uploadPath, bucketName, region);
        String completeM3u8Url = sysConfigAttribute.getTencentCdnPreFixUrl() + "/" + m3u8Url;
        commonResult = CommonResult.success(completeM3u8Url);
        logger.info("上传成功 访问url[{}]", completeM3u8Url);
        return commonResult;
    }


    @RequestMapping("/index")
    public String index() {

        return "video/index";
    }


    @ResponseBody
    @RequestMapping("/checkUploaded")
    public Object checkViedoUpload(String fileKey) {
        if (StrUtil.isEmpty(fileKey)) {
            return CommonResult.failed();
        }
        FileUploadBlock fileUploadBlock = fileUploadBlockService.checkFileUpload(fileKey);
        if (fileUploadBlock == null) {
            return CommonResult.commonResult(404L, "未查询到相关文件", null);
        }
        return CommonResult.success(fileUploadBlock);
    }

    /**
     * 上传
     *
     * @param file
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(MultipartFile file, HttpServletRequest request) throws IOException, InterruptedException {

        String suffix = request.getParameter("suffix");
        Long shardIndex = Long.valueOf(request.getParameter("shardIndex"));
        Long shardSize = Long.valueOf(request.getParameter("shardSize"));
        Long shardTotal = Long.valueOf(request.getParameter("shardTotal"));
        Long size = Long.valueOf(request.getParameter("size"));
        String sourceName = request.getParameter("sourceName");
        String key = request.getParameter("key");

        log.info("上传文件开始");
        //文件的名称
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取文件的扩展名
        String[] splits = sourceName.split("\\.");
        if (splits == null || splits.length < 2) {
            return CommonResult.failed("获取文件扩展名失败");
        }
        String ext = splits[splits.length - 1];

        User sessionUser = (User) SessionUtil.getSessionAttribute(SysConfigConstants.SESSION_USER);
        // 保存文件的路径
        String videoUploadSaveDir = sysConfigAttribute.getVideoUploadSaveDir() + File.separator
                + sessionUser.getId() + "-" + sessionUser.getUserName();
        File videoUploadSaveDirFile = new File(videoUploadSaveDir);
        if (!videoUploadSaveDirFile.exists()) {
            videoUploadSaveDirFile.mkdirs();
        }

        //分片文件名
        String saveFilePath = videoUploadSaveDir + File.separator + sourceName + "." + shardIndex;
        File saveFile = new File(saveFilePath);

        // 判断当前分片是否存在 如果存在比较 文件大小
        if (saveFile.exists()) {
            Long existsFileSize = FileUtil.size(saveFile);
            if (existsFileSize.equals(shardSize)) {
                return CommonResult.success(200, "分片已存在");
            } else {
                saveFile.delete();
            }
        }

        //上传这个图片
        file.transferTo(saveFile);

        //数据库持久化这个数据
        FileUploadBlock fileUploadBlock = new FileUploadBlock();
        fileUploadBlock.setPath(saveFile.getAbsolutePath());
        fileUploadBlock.setName(name);
        fileUploadBlock.setSuffix(ext);
        fileUploadBlock.setSize(size);
        fileUploadBlock.setCreatedAt(System.currentTimeMillis());
        fileUploadBlock.setUpdatedAt(System.currentTimeMillis());
        fileUploadBlock.setShardIndex(shardIndex.longValue());
        fileUploadBlock.setShardSize(shardSize);
        fileUploadBlock.setShardTotal(shardTotal);
        fileUploadBlock.setFileKey(key);
        fileUploadBlock.setSourceName(sourceName);
        //插入到数据库中
        fileUploadBlockService.saveOrUpdateFileUploadBlock(fileUploadBlock);
        //判断当前是不是最后一个分页 如果不是就继续等待其他分页 合并分页
        if (shardIndex.equals(shardTotal)) {
            this.merge(fileUploadBlock);
        }
        return CommonResult.success();
    }


    /**
     * @author xwder
     * 合并分页
     */
    private void merge(FileUploadBlock fileUploadBlock) throws FileNotFoundException, InterruptedException {
        //合并分片开始
        log.info("分片合并开始");
        String path = fileUploadBlock.getPath(); //获取到的路径 没有.1 .2 这样的东西
        //截取视频所在的路径
        String basePath = new File(fileUploadBlock.getPath()).getParentFile().getAbsolutePath();
        File newFile = new File(basePath + File.separator + fileUploadBlock.getSourceName());
        // 文件追加写入
        FileOutputStream outputStream = new FileOutputStream(newFile, true);

        //分片文件
        FileInputStream fileInputStream = null;
        byte[] byt = new byte[10 * 1024 * 1024];
        int len;
        Long shardTotal = fileUploadBlock.getShardTotal();
        try {
            for (int i = 0; i < shardTotal; i++) {
                // 读取第i个分片 course\6sfSqfOwzmik4A4icMYuUe.mp4.1
                fileInputStream = new FileInputStream(new File(basePath + File.separator + fileUploadBlock.getSourceName() + "." + (i + 1)));
                while ((len = fileInputStream.read(byt)) != -1) {
                    outputStream.write(byt, 0, len);
                }
            }
        } catch (IOException e) {
            log.error("分片合并异常", e);
        } finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                outputStream.close();
                log.info("IO流关闭");
            } catch (Exception e) {
                log.error("IO流关闭", e);
            }
        }
        log.info("分片结束了");
        //告诉java虚拟机去回收垃圾 至于什么时候回收 这个取决于 虚拟机的决定
        System.gc();
        //等待100毫秒 等待垃圾回收去 回收完垃圾
        Thread.sleep(100);
        log.info("删除分片开始");
        for (int i = 0; i < shardTotal; i++) {
            String filePath = basePath + File.separator + fileUploadBlock.getSourceName() + "." + (i + 1);
            File file = new File(filePath);
            boolean result = file.delete();
            log.info("删除{}，{}", filePath, result ? "成功" : "失败");
        }
        log.info("删除分片结束");
    }
}