package com.xwder.manage.message;


import com.xwder.manage.XwderManageApplication;
import com.xwder.manage.modules.book.service.intf.IChapterService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 短信测试类
 *
 * @Author: xwder
 * @Date: 2019/7/20 00:46
 * @Description:
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = XwderManageApplication.class)
public class MyQCloudSMSMessageServiceTest {

    @Autowired
    private IChapterService iChapterService;

    /**
     * 发送短信测试
     */
    @Test
    public void sendSmsMessageTest() throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        String author="烽火戏诸侯";
        String bookName = "剑来";
        String bookUrl="http://www.shuquge.com/txt/8659/index.html";
        iChapterService.bookUpdateNotice(author,bookName,bookUrl);
    }


}
