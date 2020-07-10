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

    @Value("${system.mailVerifyUrl}")
    private String mailVerifyUrl;

    @Value("${system.newUserReigsterWeChatNoticeAdmin}")
    private Boolean newUserReigsterWeChatNoticeAdmin;

    @Value("${system.adminWxPusherUid}")
    private String adminWxPusherUid;

    @Value("${novel.book.dir}")
    private String novelBookDir;

    @Value("${lottery.save.dir}")
    private String lotterySaveDir;

    @Value("${video.upload.saveDir}")
    private String videoUploadSaveDir;

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

}
