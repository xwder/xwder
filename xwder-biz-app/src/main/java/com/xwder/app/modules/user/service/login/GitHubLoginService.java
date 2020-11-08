package com.xwder.app.modules.user.service.login;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.app.modules.user.entity.UserGithub;
import com.xwder.app.modules.user.entity.UserQQ;
import com.xwder.app.modules.user.repositry.UserGithubRepositry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * github 登录
 *
 * @author xwder
 * @version 1.0
 * @date 2020-11-08 12:39
 */
@Slf4j
@Service
public class GitHubLoginService {

    /**
     * clientId 为网站应用的 clientId
     */
    private String clientId = "f183f3110afc3687e86a";
    /**
     * clientSecrets 为网站应用的 clientSecrets
     */
    private String clientSecrets = "20cb241ee789aafa148c132e067c73f49a88568c";
    /**
     * github 回调请求地址
     */
    private String gitHubLoginUrlSuffix = "login/github/getauthcode";

    @Autowired
    private UserGithubRepositry userGithubRepositry;

    /**
     * 获取登录页面github登录的url
     *
     * @return
     */
    public String getGitHubLoginUrl() {
        String url = "https://github.com/login/oauth/authorize?client_id=%s&state=xwder";
        url = String.format(url, clientId);
        log.info("github登录-获取登录页面url：{}", url);
        return url;
    }

    /**
     * github登录回调的授权码 然后获取accessToken
     *
     * @param code
     * @return
     */
    public String getAccessToken(String code, String domain) {
        String url = "https://github.com/login/oauth/access_token";
        HashMap<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("client_id", clientId);
        paramMap.put("client_secret", clientSecrets);
        paramMap.put("state", "xwder");
        paramMap.put("code", code);
        paramMap.put("redirect_uri", domain + gitHubLoginUrlSuffix);
        // access_token=6cf394540151554f03a4701c2690b626ffdf37ca&scope=&token_type=bearer
        String result = HttpUtil.post(url, paramMap);
        log.info("github登录-获取accessToken请求响应信息：{}", result);
        if (StrUtil.isNotEmpty(result) && StrUtil.containsAny(result, "access_token")) {
            log.info("github登录-获取accessToken信息成功");
            HashMap<String, String> resultMap = HttpUtil.decodeParamMap(result, "UTF-8");
            return resultMap.get("access_token");
        }
        log.error("github登录-获取accessToken失败");
        return null;
    }

    /**
     * 获取用户的信息
     *
     * @param accessToken
     * @return
     */
    public UserGithub getUserInfo(String accessToken) {
        String url = "https://api.github.com/user";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", "");
        String result = HttpRequest.get(url)
                .header("Authorization", "token " + accessToken)
                .execute().body();
        log.info("github登录-获取用户的信息响应信息：{}", result);
        JSONObject jsonObject = JSONUtil.parseObj(result);
        UserGithub userGithub = jsonObject.toBean(UserGithub.class);
        if (userGithub != null) {
            log.info("github登录-获取用户的信息解析成功");
            // 特殊的字段处理
            userGithub.setGithubId(userGithub.getId());
            userGithub.setId(null);
            return userGithub;
        }
        log.error("github登录-获取用户的信息解析失败");
        return null;
    }

    /**
     * 根据 login github用户名查找用户信息
     *
     * @param githubName app_user_github 表 login字段 为github用户名
     * @return
     */
    public UserGithub findByGithubName(String githubName) {
        return userGithubRepositry.findByLogin(githubName);
    }

    /**
     * 新增或者更新 usergithub 用户信息
     *
     * @param userGithub
     * @return
     */
    @Transactional(rollbackFor = {Exception.class})
    public UserGithub saveOrUpdateUserGithub(UserGithub userGithub) {
        return userGithubRepositry.save(userGithub);
    }
}
