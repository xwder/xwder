package com.xwder.app.modules.cloud.tencent.cos.auth;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.tencent.cloud.CosStsClient;
import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.RedisConstants;
import com.xwder.app.utils.DateUtil;
import com.xwder.app.utils.RedisUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author xwder
 * @Description: 腾讯云鉴权服务
 * @date 2020/7/4 0:42
 */
@Service
public class TencentCosAuthService {

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private RedisUtil redisUtil;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "腾讯云鉴权服务";

    /**
     * 获取cos临时凭据
     *
     * @return
     */
    public Map<String, Object> getTmpCosSecret() {
        return getTmpCosSecret(sysConfigAttribute.getTencentCosBucketName(), sysConfigAttribute.getTencentCosRegion());
    }

    /**
     * 获取cos临时凭据
     *
     * @return
     */
    public Map<String, Object> getTmpCosSecret(String bucketName, String region) {
        Long logStartTime = System.currentTimeMillis();
        logger.info("[{}] 获取cos 临时凭据,bucketName[{}],region[{}]", serviceName, bucketName, region);

        HashMap<String, Object> credMap = Maps.newHashMap();
        // 先从缓存里面读取
        String tmpSecretId = (String) redisUtil.get(RedisConstants.TENCENT_COS_TMP_SECRET_ID);
        String tmpSecretKey = (String) redisUtil.get(RedisConstants.TENCENT_COS_TMP_SECRET_KEY);
        String sessionToken = (String) redisUtil.get(RedisConstants.TENCENT_COS_TMP_SESSIONTOKEN);

        if (StrUtil.isNotEmpty(tmpSecretId)) {
            credMap.put("requestId", null);
            credMap.put("expirationDate", null);
            credMap.put("startTime", null);
            credMap.put("expiredTime", null);
            credMap.put("tmpSecretId", tmpSecretId);
            credMap.put("tmpSecretKey", tmpSecretKey);
            credMap.put("sessionToken", sessionToken);
            logger.info("[{}]从缓存获取cos临时凭据结束,耗时[{}]", serviceName, DateUtil.diffTime(logStartTime, System.currentTimeMillis()));
            return credMap;
        }

        TreeMap<String, Object> config = new TreeMap<String, Object>();
        // 云 api 密钥 SecretId
        config.put("secretId", sysConfigAttribute.getTencentAuthSecretId());
        // 云 api 密钥 SecretKey
        config.put("secretKey", sysConfigAttribute.getTencentAuthSecretKey());
        // 临时密钥有效时长，单位是秒
        config.put("durationSeconds", 1800);
        // 换成你的 bucket
        config.put("bucket", bucketName);
        // 换成 bucket 所在地区
        config.put("region", region);

        // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径，例子： a.jpg 或者 a/* 或者 * (使用通配符*存在重大安全风险, 请谨慎评估使用)
        //config.put("allowPrefixes", new String[]{"video"});
        config.put("allowPrefix", "*");

        // https://cloud.tencent.com/document/product/436/31923
        // 密钥的权限列表。简单上传和分片需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
        String[] allowActions = new String[]{
                // 简单上传
                "name/cos:PutObject",
                "name/cos:PostObject",
                "name/cos:HeadObject",
                "name/cos:GetObject",
                //
                "name/cos:PutObjectACL",
                "name/cos:GetObjectACL",
                "name/cos:OptionsObject",
                "name/cos:PostObjectRestore",
                "name/cos:DeleteObject",
                "name/cos:DeleteObject",
                // 分片上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload",
                "name/cos:AbortMultipartUpload",
                // bucket
                "name/cos:GetService",
                "name/cos:GetBucket",
                "name/cos:PutBucket",
                "name/cos:HeadBucket",
                "name/cos:DeleteBucket",
                "name/cos:PutBucketACL",
                "name/cos:GetBucketACL",
                "name/cos:PutBucketCORS",
                "name/cos:GetBucketCORS",
                "name/cos:DeleteBucketCORS",
                "name/cos:PutBucketLifecycle",
                "name/cos:GetBucketLifecycle",
                "name/cos:DeleteBucketLifecycle",
                "name/cos:ListMultipartUploads"
        };
        config.put("allowActions", allowActions);
        try {
            JSONObject credential = CosStsClient.getCredential(config);

            String requestId = credential.getString("requestId");
            Date expirationDate = DateUtil.parseDate(credential.getString("expiration"), DateUtil.format_yyyy_MM_ddTHHmmss);
            Long startTime = credential.getLong("startTime");
            Long expiredTime = credential.getLong("expiredTime");
            JSONObject credentials = credential.getJSONObject("credentials");
            tmpSecretId = credentials.getString("tmpSecretId");
            tmpSecretKey = credentials.getString("tmpSecretKey");
            sessionToken = credentials.getString("sessionToken");
            credMap = Maps.newHashMap();
            credMap.put("requestId", requestId);
            credMap.put("expirationDate", expirationDate);
            credMap.put("startTime", startTime);
            credMap.put("expiredTime", expiredTime);
            credMap.put("tmpSecretId", tmpSecretId);
            credMap.put("tmpSecretKey", tmpSecretKey);
            credMap.put("sessionToken", sessionToken);
            System.out.println(credential.toString(4));

            // 先从缓存里面读取
            redisUtil.set(RedisConstants.TENCENT_COS_TMP_SECRET_ID,tmpSecretId, RedisConstants.TENCENT_COS_TMP_CACHETIME);
            redisUtil.set(RedisConstants.TENCENT_COS_TMP_SECRET_KEY,tmpSecretKey, RedisConstants.TENCENT_COS_TMP_CACHETIME);
            redisUtil.set(RedisConstants.TENCENT_COS_TMP_SESSIONTOKEN,sessionToken, RedisConstants.TENCENT_COS_TMP_CACHETIME);

            logger.info("[{}]获取cos临时凭据成功,并设置缓存结束,耗时[{}]", serviceName, DateUtil.diffTime(logStartTime, System.currentTimeMillis()));
            return credMap;
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("[{}]获取cos临时凭据失败，错误信息[{}]", serviceName, e);
        }
        return Maps.newHashMap();
    }
}
