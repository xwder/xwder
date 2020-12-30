package com.xwder.app.modules.cloud.baidu.ocr.auth;

import com.xwder.app.attribute.SysConfigAttribute;
import com.xwder.app.consts.RedisConstants;
import com.xwder.app.utils.DateUtil;
import com.xwder.app.utils.RedisUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * 百度获取api token
 * 获取token类
 */
@Service
public class BaiduAuthService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "百度获取token";

    @Autowired
    private SysConfigAttribute sysConfigAttribute;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 获取权限token
     *
     * @return 返回示例：
     * {
     * "access_token": "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567",
     * "expires_in": 2592000
     * }
     */
    public String getApiAuth() {
        // 官网获取的 API Key 更新为你注册的
        String clientId = sysConfigAttribute.getBaiduOcrKey();
        // 官网获取的 Secret Key 更新为你注册的
        String clientSecret = sysConfigAttribute.getBaiduOcrSecret();
        return getApiAuth(clientId, clientSecret);
    }

    /**
     * 获取API访问token
     * 该token有一定的有效期，需要自行管理，当失效时需重新获取.
     *
     * @param ak - 百度云官网获取的 API Key
     * @param sk - 百度云官网获取的 Securet Key
     * @return assess_token 示例：
     * "24.460da4889caad24cccdb1fea17221975.2592000.1491995545.282335-1234567"
     */
    public String getApiAuth(String ak, String sk) {
        Long startTime = System.currentTimeMillis();
        logger.info("[{}] 获取API访问token开始,key[{}],securt[{}]", serviceName, ak, sk);

        // 取本地缓存
        String baiduOcrAccessToken = (String) redisUtil.get(RedisConstants.BAIDU_ACCESS_TOKEN_OCR);
        if (baiduOcrAccessToken != null) {
            logger.info("[{}]从缓存获取API访问token结束,耗时[{}]", serviceName, DateUtil.diffTime(startTime, System.currentTimeMillis()));
            return baiduOcrAccessToken;
        }

        // 获取token地址
        String authHost = "https://aip.baidubce.com/oauth/2.0/token?";
        String getAccessTokenUrl = authHost
                // 1. grant_type为固定参数
                + "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=" + ak
                // 3. 官网获取的 Secret Key
                + "&client_secret=" + sk;
        String accessToken = null;
        try {
            logger.info("[{}] 获取API访问token,url[{}]", serviceName, getAccessTokenUrl);
            URL realUrl = new URL(getAccessTokenUrl);
            // 打开和URL之间的连接
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 定义 BufferedReader输入流来读取URL的响应
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String result = "";
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            logger.info("[{}] 获取API访问token,返回结果[{}]", serviceName, result);
            JSONObject jsonObject = new JSONObject(result);
            accessToken = jsonObject.getString("access_token");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[{}] 获取API访问token失败,错误信息[{}]", serviceName, e);
        }

        // 设置缓存
        redisUtil.set(RedisConstants.BAIDU_ACCESS_TOKEN_OCR,accessToken, RedisConstants.BAIDU_ACCESS_TOKEN_OCR_CACHETIME);
        logger.info("[{}]获取API访问token,并设置缓存结束,耗时[{}]", serviceName, DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return accessToken;
    }

}