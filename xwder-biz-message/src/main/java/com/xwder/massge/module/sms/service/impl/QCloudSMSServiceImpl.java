package com.xwder.massge.module.sms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xwder.cloud.commmon.api.CommonResult;
import com.xwder.massge.module.sms.config.MyQcloudSMSConfig;
import com.xwder.massge.module.sms.service.QCloudSMSService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.Base64.Encoder;
import java.util.Base64.Decoder;

/**
 * @Author: xwder
 * @Date: 2019/7/26 00:03
 * @Description:
 */
@Service
public class QCloudSMSServiceImpl implements QCloudSMSService {

    @Autowired
    private MyQcloudSMSConfig myQcloudSMSConfig;

    private String calcAuthorization(String source, String secretId, String secretKey, String datetime) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
        String signStr = "x-date: " + datetime + "\n" + "x-source: " + source;
        Mac mac = Mac.getInstance("HmacSHA1");
        Key sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), mac.getAlgorithm());
        mac.init(sKey);
        byte[] hash = mac.doFinal(signStr.getBytes("UTF-8"));

        // jdk11
        Encoder encoder = Base64.getEncoder();
        String sig = encoder.encodeToString(hash);
        // Jdk8
        //String sig = new BASE64Encoder().encode(hash);

        String auth = "hmac id=\"" + secretId + "\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"" + sig + "\"";
        return auth;
    }

    private String urlEncode(Map<?, ?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
                    URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
                    URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();
    }


    //    @HystrixCommand(fallbackMethod = "sendSMSFallback")
    @Override
    public CommonResult sendSMS(String phone, String content) {

        String source = "market";
        BufferedReader in = null;

        try {
            Calendar cd = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            String datetime = sdf.format(cd.getTime());
            // 签名
            String auth = calcAuthorization(source, myQcloudSMSConfig.getSecretId(), myQcloudSMSConfig.getSecretKey(), datetime);

            // 查询参数
            Map<String, String> queryParams = new HashMap<String, String>();
            queryParams.put("content", content);
            queryParams.put("mobile", phone);
            // body参数
            Map<String, String> bodyParams = new HashMap<String, String>();

            // url参数拼接
            String url = myQcloudSMSConfig.getUrl();
            if (!queryParams.isEmpty()) {
                url += "?" + urlEncode(queryParams);
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httpGet.addHeader("X-Source", source);
            httpGet.addHeader("X-Date", datetime);
            httpGet.addHeader("Authorization", auth);

            String result = StringUtils.EMPTY;
            try {
                CloseableHttpResponse response = httpClient.execute(httpGet);
                int status = response.getStatusLine().getStatusCode();
                HttpEntity responseEntity = response.getEntity();
                result = EntityUtils.toString(responseEntity, "UTF-8");
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jsonObject = JSONObject.parseObject(result);
            System.out.println(jsonObject.toJSONString());
            Integer resultCode = jsonObject.getInteger("result");
            if (resultCode == 0) {
                return CommonResult.success();
            } else {
                return CommonResult.failed(jsonObject.get("errmsg").toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return CommonResult.failed(e.getMessage());
        }
    }

    public CommonResult sendSMSFallback(String phone, String content) {
        return CommonResult.failed("发送失败");
    }
}
