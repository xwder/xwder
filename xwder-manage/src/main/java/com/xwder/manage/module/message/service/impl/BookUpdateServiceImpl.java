package com.xwder.manage.module.message.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xwder.api.message.mail.MessageServiceApi;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.message.ResultUtil;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.manage.module.message.service.BookUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: xwder
 * @Date: 2019/7/14 23:38
 * @Description:
 */
@Service
public class BookUpdateServiceImpl implements BookUpdateService {

    @Autowired
    private MessageServiceApi messageServiceApi;

    @Override
    public List<Map> getBookUpdateInfo(String author, String bookName) {

        String url = "http://119.29.136.137:8888/book/烽火戏诸侯/剑来";

        String json = HttpClientUtil.doGet(url);

        JSONObject jsonObject = JSONObject.parseObject(json);

        Integer code = (Integer) jsonObject.get("code");

        if (code == 101){
            return null;
        }

        if (code == 200) {
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            List<Map> list = new ArrayList<>();

            if(jsonArray.size() == 0){
                return null;
            }

            int size = jsonArray.size();
            int i = 0;
            for (; i < size; i++) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                String chapterName = (String) jsonObject1.get("chapterName");
                String author_ = (String) jsonObject1.get("author");
                String bookName_ = (String) jsonObject1.get("bookName");
                Integer isUpdate = (Integer) jsonObject1.get("iSupdate");
                String updateTime = (String) jsonObject1.get("updateTime");
                Map<String, Object> map = new HashMap<>();
                map.put("chapterName", chapterName);
                map.put("isUpdate", isUpdate);
                map.put("updateTime", updateTime);
                map.put("author", author_);
                map.put("bookName", bookName_);
                list.add(map);
            }
            return list;
        }


        return null;
    }

    @Override
    public Result sendBookUpdateMailMessage(List<Map> list, Map mailMap) {

        String msg = "您关注的小说 已更新，最新章节  ";
        if (!list.isEmpty()) {
            for (Map map : list) {
                msg = msg + map.get("chapterName") + " 更新时间  " + map.get("updateTime") + " <br> ";
            }
        }

        String to = (String) mailMap.get("to");
        String subject = (String) mailMap.get("subject");
        String content = "您关注的小说已更新，最新章节 " + msg + "";
        Result result = messageServiceApi.sendSimpleMail(to, subject, content);

        return result;
    }


    @Override
    public Result sendBooUpdateMessageWithMailAndBooInfo(String author, String bookName, String to, String subject) {

        List<Map> list = getBookUpdateInfo(author, bookName);

        if (list == null || list.isEmpty()) {
            return ResultUtil.error(501, "您关注的  "+author+" 的 "+bookName+" 小说没有更新！");

        }
        Map<String, Object> mailMap = new HashMap<>();
        mailMap.put("to", to);
        mailMap.put("subject", subject);

        Result result = sendBookUpdateMailMessage(list, mailMap);

        return result;
    }

}
