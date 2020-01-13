package com.xwder.manage.modules.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.common.utils.StringUtils;
import com.xwder.manage.common.utils.bean.BeanUtils;
import com.xwder.manage.modules.book.config.BookServiceUrlConfig;
import com.xwder.manage.modules.book.contant.SMSConstant;
import com.xwder.manage.modules.book.dto.BookChapterDto;
import com.xwder.manage.modules.book.dto.BookInfoDto;
import com.xwder.manage.modules.book.service.intf.IChapterService;
import com.xwder.manage.modules.message.service.intl.AlertOverService;
import com.xwder.manage.modules.message.service.intl.MailService;
import com.xwder.manage.modules.message.service.intl.SmsMessageService;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author wande
 * @version 1.0
 * @date 2020/01/08
 */
@Slf4j
@Service
public class IChapterServiceImpl implements IChapterService {

    @Autowired
    private BookServiceUrlConfig bookServiceUrlConfig;

    @Autowired
    private SmsMessageService smsMessageService;

    @Autowired
    private MailService mailService;

    @Autowired
    private AlertOverService alertOverService;

    @Override
    public List<BookChapterDto> getLatestChapters(String author, String bookName, String bookUrl) {
        String url = new StringBuilder().append(bookServiceUrlConfig.getGatewayUrl())
                .append(bookServiceUrlConfig.getLatestChapters())
                .append("?bookUrl=")
                .append(bookUrl)
                .append("&author=")
                .append(author)
                .append("&bookName=")
                .append(bookName)
                .toString();
        String result = null;
        try {
            result = HttpClientUtil.doGet(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(result)) {
            return Lists.newArrayList();
        }
        Map map = JSON.parseObject(result, Map.class);
        Integer code = (Integer) map.get("code");
        if (code == 200) {
            return (List<BookChapterDto>) map.get("data");
        }
        return Lists.newArrayList();
    }

    @Override
    public void bookUpdateNotice(String author, String bookName, String bookUrl) {
        List latestChapters = getLatestChapters(author, bookName, bookUrl);
        if (CollectionUtils.isNotEmpty(latestChapters)) {
            // 发送消息
            // 发送对象后面可以配置再数据库中
            String phone1 = "13509433172";
            String phone2 = "18083024504";
            StringBuffer chapterName = new StringBuffer();
            for (Object object : latestChapters) {
                JSONObject jsonObject = (JSONObject) object;
                BookChapterDto latestChapterDto = JSON.toJavaObject(jsonObject, BookChapterDto.class);
                chapterName.append(latestChapterDto.getChapterName().replace(" ", ""));
            }

            String content = String.format(SMSConstant.SMS_TEMPLATE_ONE, chapterName.toString());
            // 最新章节 sms 字数有点短。。。
            JSONObject obj = (JSONObject) latestChapters.get(latestChapters.size() - 1);
            BookChapterDto smsLatestChapterDto = JSON.toJavaObject(obj, BookChapterDto.class);
            String smsContent = smsLatestChapterDto.getChapterName().replace(" ", "");

            try {
                boolean sendSMSResult = smsMessageService.sendSMS(phone1, smsContent);
                if (!sendSMSResult) {
                    smsMessageService.sendSMS(phone2, content);
                }
            } catch (Exception e) {
                log.error("发送短信失败,[{}]-[{}]-[{}]", phone1, content, e);
            }
            // 发送邮件
            mailService.sendSimpleMail("1123511540@qq.com", "更新", content);

            // 发送alertover
            for (Object object : latestChapters) {
                JSONObject jsonObject = (JSONObject) object;
                BookChapterDto latestChapterDto = JSON.toJavaObject(jsonObject, BookChapterDto.class);
                alertOverService.sendStrMessge(bookName + "-" + latestChapterDto.getChapterName(), latestChapterDto.getChapterContent());
            }

        }
    }

    @Override
    public TableDataInfo listChapters(int PageNum, int pageSize, BookChapterDto bookChapterDto) throws Exception {
        String url = bookServiceUrlConfig.getGatewayUrl() + bookServiceUrlConfig.getListChapter();
        Map paramMap = BeanUtils.beanToMapWithNotNullProperties(bookChapterDto);
        String result = HttpClientUtil.doGet(url, paramMap);
        Map map = JSON.parseObject(result, Map.class);
        if ((int) map.get("code") == 200) {
            Map data = (Map) map.get("data");
            TableDataInfo tableDataInfo = TableDataInfo.builder()
                    .rows((List<BookChapterDto>) data.get("list"))
                    .total((long) ((int) data.get("total")))
                    .code(0)
                    .build();
            return tableDataInfo;
        }

        return TableDataInfo.builder()
                .code(500)
                .rows(Lists.newArrayList())
                .build();
    }
}