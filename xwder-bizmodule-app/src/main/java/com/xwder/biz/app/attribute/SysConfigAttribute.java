package com.xwder.biz.app.attribute;

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

    @Value("${novel.book.dir}")
    private String novelBookDir;

    @Value("${lottery.save.dir}")
    private String lotterySaveDir;

    @Value("${video.upload.tempdir}")
    private String videoUploadTempDir;

    @Value("${baidu.cor.key}")
    private String baiduCorKey;

    @Value("${baidu.cor.secret}")
    private String baiduCorSecret;

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
