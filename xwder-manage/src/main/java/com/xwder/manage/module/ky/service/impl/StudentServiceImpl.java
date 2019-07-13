package com.xwder.manage.module.ky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.page.PageInfo;
import com.xwder.framework.utils.request.HttpClientUtil;
import com.xwder.manage.common.utils.StringUtils;
import com.xwder.manage.common.utils.message.Result;
import com.xwder.manage.module.ky.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @Author: xwder
 * @Date: 2019/7/10 23:57
 * @Description:
 */
@Service
public class StudentServiceImpl implements StudentService {

    private static final String REST_URL_PREFIX = "http://XWDER-PROVIDER-CQUST";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public PageInfo findAll(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {


        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        // HttpClient 方式
        // String json = HttpClientUtil.doGet(REST_URL_PREFIX + "/ky/student/page/" + pageNum + "/" + pageSize);

        String json = restTemplate.getForObject(REST_URL_PREFIX + "/ky/student/page/" + pageNum + "/" + pageSize, String.class);

        if (!StringUtils.isBlank(json)) {

            JSONObject jsonObject = JSONObject.parseObject(json);

            Integer total = (Integer) jsonObject.getJSONObject("data").get("total");
            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
            List<KyStudent> list = new ArrayList<>();
            for(int i=0; i<jsonArray.size(); i++){
                String itemStr = jsonArray.get(i).toString();
                KyStudent kyStudent = JSON.parseObject(itemStr, KyStudent.class);
                list.add(kyStudent);
            }

            PageInfo pageInfo = PageInfo.builder().total(total).data(list).build();

            return pageInfo;
        }

        return null;
    }
}
