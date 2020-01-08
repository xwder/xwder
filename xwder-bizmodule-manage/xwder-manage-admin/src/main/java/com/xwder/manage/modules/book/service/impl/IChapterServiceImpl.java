package com.xwder.manage.modules.book.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xwder.manage.common.utils.StringUtils;
import com.xwder.manage.modules.book.config.BookServiceUrlConfig;
import com.xwder.manage.modules.book.contant.SMSConstant;
import com.xwder.manage.modules.book.dto.BookChapterDto;
import com.xwder.manage.modules.book.service.intf.IChapterService;
import com.xwder.manage.modules.message.service.intl.SmsMessageService;
import com.xwder.manage.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        String result = HttpClientUtil.doGet(url);
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
        List<BookChapterDto> latestChapters = getLatestChapters(author, bookName, bookUrl);
        if (CollectionUtils.isNotEmpty(latestChapters)) {
            // 发送消息
            // 发送对象后面可以配置再数据库中
            String phone1 = "13509433172";
            String phone2 = "18083024504";
            StringBuffer chapterName = new StringBuffer();
            for (BookChapterDto latestChapterDto : latestChapters) {
                chapterName.append(latestChapterDto.getChapterName().replace(" ", ""));
            }
            String content = String.format(SMSConstant.SMS_TEMPLATE_ONE, chapterName.toString());
            try {
                boolean sendSMSResult = smsMessageService.sendSMS(phone1, content);
                if (!sendSMSResult) {
                    smsMessageService.sendSMS(phone2, content);
                }
            } catch (Exception e) {
                log.error("发送短信失败,[{}]-[{}]-[{}]", phone1, content, e);
            }
        }
    }
}
