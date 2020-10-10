package com.xwder.app.modules.cloud.tencent.cos.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.exception.CosClientException;
import com.qcloud.cos.exception.CosServiceException;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.xwder.app.advice.BizException;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.common.result.ResultCode;
import com.xwder.app.modules.cloud.tencent.cos.auth.TencentCosAuthService;
import com.xwder.app.modules.cloud.tencent.cos.domain.CosFile;
import com.xwder.app.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 腾讯云cos上传文件
 */
@Service
public class TencentCosService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "腾讯云cos";

    private COSClient cosClient;

    @Autowired
    private TencentCosAuthService tencentCosAuthService;

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    /**
     * 腾讯云 COSClient
     *
     * @param bucketName
     * @param regionName
     * @return
     */
    private COSClient getCosClient(String bucketName, String regionName) {
        bucketName = StrUtil.isEmpty(bucketName) ? sysConfigAttribute.getTencentCosBucketName() : bucketName;
        regionName = StrUtil.isEmpty(regionName) ? sysConfigAttribute.getTencentCosRegion() : regionName;
        Map<String, Object> tmpCosSecret;
        tmpCosSecret = tencentCosAuthService.getTmpCosSecret(sysConfigAttribute.getTencentCosBucketName(),
                sysConfigAttribute.getTencentCosRegion());
        if (tmpCosSecret.size() == 0) {
            throw new BizException(ResultCode.ERROR);
        }
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        String tmpSecretId = (String) tmpCosSecret.get("tmpSecretId");
        String tmpSecretKey = (String) tmpCosSecret.get("tmpSecretKey");
        String sessionToken = (String) tmpCosSecret.get("sessionToken");
        // 1 传入获取到的临时密钥 (tmpSecretId, tmpSecretKey, sessionToken)
        BasicSessionCredentials cred = new BasicSessionCredentials(tmpSecretId, tmpSecretKey, sessionToken);
        // 2 设置 bucket 的区域, COS 地域的简称请参阅 https://cloud.tencent.com/document/product/436/6224
        // clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set方法, 使用可参阅源码或者常见问题 Java SDK部分
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        // 3 生成 cos 客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }

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
     * 上传文件
     *
     * @param uploadFile 上传的文件路径
     * @param uploadPath 上传到 COS 上对象键
     * @param bucketName bucket
     * @param regionName 存储位置区域
     * @return 上传成功的 eTag
     */
    public String uploadTenCos(File uploadFile, String uploadPath, String bucketName, String regionName) {
        COSClient cosClient = getCosClient(bucketName, regionName);
        // 指定要上传的文件
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uploadPath, uploadFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        String eTag = putObjectResult.getETag();
        cosClient.shutdown();
        return eTag;
    }

    /**
     * 获取 bucketName 下的 prefix 前缀下的文件列表
     * https://cloud.tencent.com/document/product/436/7734
     * https://console.cloud.tencent.com/api/explorer?Product=cos&Version=2018-11-26&Action=GetBucket&SignVersion=
     *
     * @param bucketName Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
     * @param regionName 区域
     * @param prefix     前缀匹配，用来规定返回的文件前缀地址
     * @param MaxKeys    单次返回最大的条目数量，默认1000
     * @return
     */
    public List<CosFile> getBucketFileList(String bucketName, String regionName, String prefix, Integer MaxKeys) {
        COSClient cosClient = getCosClient(bucketName, regionName);
        bucketName = StrUtil.isEmpty(bucketName) ? sysConfigAttribute.getTencentCosBucketName() : bucketName;
        ListObjectsRequest listObjectsRequest = new ListObjectsRequest();
        // 设置bucket名称
        listObjectsRequest.setBucketName(bucketName);
        prefix = "image/";

        if (StrUtil.isNotEmpty(prefix)) {
            // prefix表示列出的object的key以prefix开始
            listObjectsRequest.setPrefix(prefix);
        }else {
            listObjectsRequest.setPrefix("/");
        }
        // deliter表示分隔符, 设置为/表示列出当前目录下的object, 设置为空表示列出所有的object
        listObjectsRequest.setDelimiter(null);
        // 设置最大遍历出多少个对象, 一次listobject最大支持1000
        listObjectsRequest.setMaxKeys(1000);
        ObjectListing objectListing = null;
        ArrayList<CosFile> cosFileList = Lists.newArrayListWithCapacity(MaxKeys);
        do {
            try {
                objectListing = cosClient.listObjects(listObjectsRequest);
            } catch (CosServiceException e) {
                e.printStackTrace();
                return Lists.newArrayList();
            } catch (CosClientException e) {
                e.printStackTrace();
                return Lists.newArrayList();
            }
            // common prefix表示表示被delimiter截断的路径, 如delimter设置为/, common prefix则表示所有子目录的路径
            List<String> commonPrefixs = objectListing.getCommonPrefixes();

            // object summary表示所有列出的object列表
            List<COSObjectSummary> cosObjectSummaries = objectListing.getObjectSummaries();
            for (COSObjectSummary cosObjectSummary : cosObjectSummaries) {
                Owner owner = cosObjectSummary.getOwner();
                // 文件的路径key
                String key = cosObjectSummary.getKey();
                // 文件的etag
                String etag = cosObjectSummary.getETag();
                // 文件的长度
                long fileSize = cosObjectSummary.getSize();
                // 文件的存储类型
                String storageClasses = cosObjectSummary.getStorageClass();
                // 对象最后修改时间
                Date lastModified = cosObjectSummary.getLastModified();

                CosFile cosFile = CosFile.builder()
                        .key(key).etag(etag)
                        .fileSize(fileSize)
                        .storageClasses(storageClasses)
                        .id(owner.getId())
                        .displayName(owner.getDisplayName())
                        .lastModified(lastModified)
                        .build();
                cosFileList.add(cosFile);
            }

            String nextMarker = objectListing.getNextMarker();
            listObjectsRequest.setMarker(nextMarker);
        } while (objectListing.isTruncated());
        return cosFileList;
    }

    /**
     * 删除文件
     * @param bucketName Bucket的命名格式为 BucketName-APPID ，此处填写的存储桶名称必须为此格式
     * @param regionName 区域
     * @param key 文件key image/blog/xwder/1-20200930113010832.png
     */
    public void deleteCosFile(String bucketName, String regionName, String key){
        COSClient cosClient = getCosClient(bucketName, regionName);
        bucketName = StrUtil.isEmpty(bucketName) ? sysConfigAttribute.getTencentCosBucketName() : bucketName;
        cosClient.deleteObject(bucketName,key);
    }
}
