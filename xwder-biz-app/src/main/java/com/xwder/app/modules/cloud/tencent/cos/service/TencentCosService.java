package com.xwder.app.modules.cloud.tencent.cos.service;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.xwder.app.modules.cloud.tencent.cos.auth.TencentCosAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

/**
 * 腾讯云cos上传文件
 */
@Service
public class TencentCosService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "腾讯云cos上传文件";

    @Autowired
    private TencentCosAuthService tencentCosAuthService;

    /**
     * 创建bucket TODO 需要测试
     */
    public void createBucket(String bucketName) {
        // 1 初始化用户身份信息（secretId, secretKey）。
        String secretId = "AKIDKu1wOP5bBRwDYcaJVBCYqA4q7TAsD-Hhs6kk0Mpgvt744hcsE2JBHNyqi5anIf8Q";
        String secretKey = "/wY7PAQ2oY+Bv4wAOysIxkr1dULG8FwW+AQvl6P1QfE=";
        String cosRegion = "ap-chongqing";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region(cosRegion);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
        // 设置 bucket 的权限为 Private(私有读写), 其他可选有公有读私有写, 公有读写
        createBucketRequest.setCannedAcl(CannedAccessControlList.PublicRead);
        try {
            Bucket bucketResult = cosClient.createBucket(createBucketRequest);
        } catch (CosServiceException serverException) {
            serverException.printStackTrace();
        } catch (CosClientException clientException) {
            clientException.printStackTrace();
        }
    }

    /**
     * @param uploadFile 上传的文件路径
     * @param uploadPath 上传到 COS 上对象键
     * @param bucketName bucket
     * @param regionName 存储位置区域
     * @return 上传成功的 eTag
     */
    public String uploadTenCos(File uploadFile, String uploadPath, String bucketName, String regionName) {
        Map<String, Object> tmpCosSecret = tencentCosAuthService.getTmpCosSecret(bucketName, regionName);
        if (tmpCosSecret.size() == 0) {
            return null;
        }
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = (String) tmpCosSecret.get("tmpSecretId");
        String tmpSecretKey = (String) tmpCosSecret.get("tmpSecretKey");
        String sessionToken = (String) tmpCosSecret.get("sessionToken");
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的区域, COS 地域的简称请参阅 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参阅源码或者常见问题 Java SDK 部分
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadPath, uploadFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        String eTag = putObjectResult.getETag();
        cosClient.shutdown();
        return eTag;
    }
}
