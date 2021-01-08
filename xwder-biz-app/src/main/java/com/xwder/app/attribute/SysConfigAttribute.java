package com.xwder.app.attribute;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/07
 */
@Data
@Configuration
public class SysConfigAttribute {

    @Value("${system.name}")
    private String systemName;

    @Value("${system.chineseName}")
    private String chineseName;

    @Value("${system.sessionTokenName}")
    private String sessionTokenName;

    @Value("${system.mailVerifyUrl}")
    private String mailVerifyUrl;

    @Value("${system.newUserReigsterWeChatNoticeAdmin}")
    private Boolean newUserReigsterWeChatNoticeAdmin;

    @Value("${system.adminWxPusherUid}")
    private String adminWxPusherUid;

    @Value("${system.staticResourcePath}")
    private String staticResourcePath;

    @Value("${system.staticResourceCdnPath}")
    private String staticResourceCdnPath;

    @Value("${novel.book.dir}")
    private String novelBookDir;

    @Value("${upload.saveBaseDir}")
    private String uploadSaveBaseDir;

    /** 视频上传保存服务器的besedir 完整的路径+/+user主键编号 */
    @Value("${upload.videoUploadSaveDir}")
    private String videoUploadSaveDir;

    /** lottery上传保存服务器的besedir 完整的路径+/+user主键编号 */
    @Value("${upload.lotterySaveDir}")
    private String lotterySaveDir;

    @Value("${video.videoSplitTime}")
    private Integer videoSplitTime;

    @Value("${baidu.ocr.key}")
    private String baiduOcrKey;

    @Value("${baidu.ocr.secret}")
    private String baiduOcrSecret;

    @Value("${tencent.auth.secretId}")
    private String tencentAuthSecretId;

    @Value("${tencent.auth.secretKey}")
    private String tencentAuthSecretKey;

    @Value("${tencent.cos.bucketName}")
    private String tencentCosBucketName;

    @Value("${tencent.cos.region}")
    private String tencentCosRegion;

    @Value("${tencent.cdn.prefixurl}")
    private String tencentCdnPreFixUrl;

    @Value("${tencent.cos.blogImageDir}")
    private String blogImageDir;

    @Value("${spring.jpa.database}")
    private String sysDbType;


}
