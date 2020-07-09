package com.xwder.manage.modules.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xwder.manage.common.core.page.TableDataInfo;
import com.xwder.manage.common.utils.StringUtils;
import com.xwder.manage.common.utils.bean.BeanUtils;
import com.xwder.manage.modules.book.config.BookServiceUrlConfig;
import com.xwder.manage.modules.book.contant.SMSConstant;
import com.xwder.manage.modules.book.dto.BookChapterDto;
import com.xwder.manage.modules.book.service.intf.IChapterService;
import com.xwder.manage.modules.message.service.intl.AlertOverService;
import com.xwder.manage.modules.message.service.intl.MailService;
import com.xwder.manage.modules.message.service.intl.SmsMessageService;
import com.xwder.manage.modules.mq.config.RabbitConfig;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    RabbitTemplate rabbitTemplate;

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

            String content = String.format(SMSConstant.SMS_TEMPLATE_ONE, bookName + chapterName.toString());
            // 最新章节 sms 字数有点短。。。
            JSONObject obj = (JSONObject) latestChapters.get(latestChapters.size() - 1);
            BookChapterDto smsLatestChapterDto = JSON.toJavaObject(obj, BookChapterDto.class);
            String smsContent = String.format(SMSConstant.SMS_TEMPLATE_ONE, bookName + smsLatestChapterDto.getChapterName().replace(" ", ""));

            try {
                Map<String, String> smsMap = Maps.newHashMap();
                smsMap.put("phone", phone1);
                smsMap.put("content", smsContent);
                rabbitTemplate.convertAndSend(RabbitConfig.XWDER_EXCHANGE_BOOK, RabbitConfig.XWDER_SMS_QUEUE_BOOK_UPDATE, smsMap);
            } catch (Exception e) {
                log.error("发送短信失败,[{}]-[{}]-[{}]", phone1, content, e);
            }
            // 发送邮件
            Map mailMap = Maps.newHashMap();
            mailMap.put("to", "1123511540@qq.com");
            mailMap.put("subject", "小说更新");
            mailMap.put("content", content);
            rabbitTemplate.convertAndSend(RabbitConfig.XWDER_EXCHANGE_BOOK, RabbitConfig.XWDER_EMAIL_QUEUE_BOOK_UPDATE, mailMap);

            // 发送alertover
            for (Object object : latestChapters) {
                JSONObject jsonObject = (JSONObject) object;
                BookChapterDto latestChapterDto = JSON.toJavaObject(jsonObject, BookChapterDto.class);
                Map map = Maps.newHashMap();
                map.put("title", bookName + "-" + latestChapterDto.getChapterName());
                map.put("content", latestChapterDto.getChapterContent());
                rabbitTemplate.convertAndSend(RabbitConfig.XWDER_EXCHANGE_BOOK, RabbitConfig.XWDER_ALERTOVER_QUEUE_BOOK_UPDATE, mailMap);
            }

            // 发送WeChat通知
            String uid = "";
            Map weChatMap = Maps.newHashMap();
            weChatMap.put("uid", uid);
            weChatMap.put("msg", content);
            rabbitTemplate.convertAndSend(RabbitConfig.XWDER_EXCHANGE_BOOK, RabbitConfig.XWDER_WECHAT_QUEUE_BOOK_UPDATE, weChatMap);

        }
    }

    @Override
    public TableDataInfo listChapters(int pageNum, int pageSize, BookChapterDto bookChapterDto) throws Exception {
        String url = bookServiceUrlConfig.getGatewayUrl() + bookServiceUrlConfig.getListChapter();
        Map paramMap = BeanUtils.beanToMapWithNotNullProperties(bookChapterDto);
        paramMap.put("pageNum", String.valueOf(pageNum));
        paramMap.put("pageSize", String.valueOf(pageSize));
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
