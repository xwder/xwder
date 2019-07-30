package com.xwder.manage.module.cqust.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.xwder.api.cqust.StudentServiceApi;
import com.xwder.framework.common.constan.Constant;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.message.Result;
import com.xwder.framework.utils.page.PageData;
import com.xwder.manage.module.cqust.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private StudentServiceApi studentServiceApi;

    @Override
    public PageData listStudent(Integer pageNum, Integer pageSize, String sortField, Sort.Direction order) {


        if (pageNum == null) {
            pageNum = Constant.DEFAULT_PAGE_NUM;
        }
        if (pageSize == null) {
            pageSize = Constant.DEFAULT_PAGE_SIZE;
        }

        // HttpClient 方式
        // String json = HttpClientUtil.doGet(REST_URL_PREFIX + "/ky/student/page/" + pageNum + "/" + pageSize);

        // restTemplate 调用方式
//        String json = restTemplate.getForObject(REST_URL_PREFIX + "/ky/student/page/" + pageNum + "/" + pageSize, String.class);
//
//        if (!StringUtils.isBlank(json)) {
//
//            JSONObject jsonObject = JSONObject.parseObject(json);
//
//            Integer total = (Integer) jsonObject.getJSONObject("data").get("total");
//            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("data");
//            List<KyStudent> list = new ArrayList<>();
//            for(int i=0; i<jsonArray.size(); i++){
//                String itemStr = jsonArray.get(i).toString();
//                KyStudent kyStudent = JSON.parseObject(itemStr, KyStudent.class);
//                list.add(kyStudent);
//            }
//
//            PageInfo pageInfo = PageInfo.builder().total(total).data(list).build();
//
//            return pageInfo;
//        }

        Result<KyStudent> result = studentServiceApi.listByPage(pageNum, pageSize, null, null);

        if(result.getCode() != 200){
            PageData pageInfo = PageData.builder().total(0).data(null).build();
            return pageInfo;
        }

        LinkedHashMap linkedHashMap = (LinkedHashMap) result.getData();
        int total = (int) linkedHashMap.get("total");

        ArrayList<Map> arrayList = (ArrayList<Map>) linkedHashMap.get("data");

        List<KyStudent> list = new ArrayList<>();

        for (Map map : arrayList) {
            JSONObject jsonObject = new JSONObject(map);
            KyStudent kyStudent = jsonObject.toJavaObject(KyStudent.class);
            list.add(kyStudent);
        }
        PageData pageInfo = PageData.builder().total(total).data(list).build();
        return pageInfo;
    }
}
