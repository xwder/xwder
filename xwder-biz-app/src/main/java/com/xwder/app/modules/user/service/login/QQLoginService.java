package com.xwder.app.modules.user.service.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xwder.app.modules.user.entity.UserQQ;
import com.xwder.app.modules.user.repositry.UserQQRepositry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * QQ 第三方登录接入
 *
 * @author xwder
 * @version 1.0
 * @date 2020-11-06 19:04
 */
@Slf4j
@Service
public class QQLoginService {

    /**
     * appId 为网站应用的APP ID
     */
    private String appId = "101787029";
    /**
     * appKey 为网站应用的APP key
     */
    private String appKey = "6f34fdd80c8c9338a5cde37bfabe4f9d";
    /**
     * 获取accessToken回调url后缀
     */
    private String accessTokenCallbackUrlSuffix = "login/qq/getaccesstoken";

    /**
     * qq 回调请求地址
     */
    private String qqLoginUrlSuffix = "login/qq/getauthcode";

    @Autowired
    private UserQQRepositry userQQRepositry;

    /**
     * 获取登录页面QQ登录的url
     *
     * @param domainUrl
     * @return
     */
    public String getQQLoginUrl(String domainUrl) {
        String url = "https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id=%s&redirect_uri=%s&state=%s&scope=%s";
        String redirectUrl = "";
        try {
            redirectUrl = URLEncoder.encode(domainUrl + qqLoginUrlSuffix, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url encode error", e);
            return "/index.html";
        }
        String scope = "get_user_info,get_vip_info,get_vip_info,list_album,upload_pic,add_album,list_photo";
        String state = "xwder";
        url = String.format(url, appId, redirectUrl, state, scope);
        log.info("QQ登录-获取登录页面url：{}", url);
        return url;
    }

    /**
     * qq回调的授权码 然后获取accessToken
     *
     * @param code
     * @return
     */
    public String getAccessToken(String code, String domain) {
        String encodeUrl = "";
        try {
            encodeUrl = URLEncoder.encode(domain + accessTokenCallbackUrlSuffix, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("QQ登录-url encode err url:{}", domain + accessTokenCallbackUrlSuffix);
        }
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&" +
                "client_id=%s&client_secret=%s&code=%s&state=xwder&redirect_uri=%s";
        url = String.format(url, appId, appKey, code, encodeUrl);
        String result = HttpUtil.get(url);
        log.info("QQ登录-获取accessToken请求响应信息：{}", result);
        // access_token=DBF1D46E9D7C1580CF3C8120CDE5BDE6&expires_in=7776000&refresh_token=EA5A10EC817394B4239C274EAD5E711A
        if (StrUtil.isNotEmpty(result)) {
            String[] splits = result.split("&");
            if (splits.length == 3) {
                String[] accessTokens = splits[0].split("=");
                if (accessTokens.length == 2) {
                    String accessToken = accessTokens[1];
                    log.info("QQ登录-获取accessToken成功");
                    return accessToken;
                }
            }
        }
        log.error("QQ登录-获取accessToken失败");
        return null;
    }

    /**
     * 获取用户openId
     *
     * @param accessToken
     * @return
     */
    public String getOpenID(String accessToken) {
        String url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        // callback( {"client_id":"101787029","openid":"76C91088EF7B42DFDAD53AEDE03B354E"} )
        String result = HttpUtil.get(url);
        log.info("QQ登录-获取用户的OpenId响应信息：{}", result);
        result = result.replace("callback(", "");
        result = result.replace(");", "");
        JSONObject jsonObject = JSONUtil.parseObj(result);
        String openId = jsonObject.getStr("openid");
        if (StrUtil.isNotEmpty(openId)) {
            log.info("QQ登录-获取用户openId成功：{}", openId);
            return openId;
        }
        log.error("QQ登录-获取用户openId失败：{}", openId);
        return null;
    }

    /**
     * 获取用户的信息
     *
     * @param accessToken
     * @param openId
     * @return
     */
    public UserQQ getUserInfo(String accessToken, String openId) {
        String url = "https://graph.qq.com/user/get_user_info?access_token=%s&oauth_consumer_key=%s&openid=%s";
        url = String.format(url, accessToken, appId, openId);
        String result = HttpUtil.get(url);
        log.info("QQ登录-获取用户的信息成功：{}", result);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        UserQQ userQQ = jsonObject.toBean(UserQQ.class);
        if (userQQ != null) {
            userQQ.setOpenId(openId);
            userQQ.setFigureurl1(jsonObject.getStr("figureurl_1"));
            userQQ.setFigureurl2(jsonObject.getStr("figureurl_2"));
            userQQ.setFigureurlQq1(jsonObject.getStr("figureurl_qq_1"));
            userQQ.setFigureurlQq2(jsonObject.getStr("figureurl_qq_2"));
        }
        return userQQ;
    }

    /**
     * 根据openid查找QQ用户信息
     *
     * @param openId
     * @return
     */
    public UserQQ findByOpenId(String openId) {
        return userQQRepositry.findByOpenId(openId);
    }

    /**
     * 保存或者更新userQQ
     *
     * @param userQQ
     * @return
     */
    public UserQQ saveOrUpdateUserQQ(UserQQ userQQ) {
        return userQQRepositry.save(userQQ);
    }
}
