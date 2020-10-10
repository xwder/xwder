package com.xwder.app.modules.test;

import com.xwder.app.modules.cloud.baidu.ocr.auth.BaiduAuthService;
import com.xwder.app.modules.cloud.baidu.ocr.auth.BaiduCommonTextOCRService;
import com.xwder.app.modules.cloud.tencent.cos.auth.TencentCosAuthService;
import com.xwder.app.modules.cloud.tencent.cos.service.TencentCosService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@RequestMapping(value = "/test")
@RestController
public class TestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaiduAuthService baiduAuthService;

    @Autowired
    private BaiduCommonTextOCRService baiduCommonTextOCRService;

    @Autowired
    private TencentCosAuthService tencentCosAuthService;

    @Autowired
    private TencentCosService tencentCosService;

    @RequestMapping("")
    public Object test(@RequestParam("serviceType") String serviceType) {
        logger.info("serviceType [{}]", serviceType);
        Object object = new Object();
        switch (serviceType) {
            case "baiduAccessToken":
                String accessToke = baiduAuthService.getApiAuth();
                object = accessToke;
                break;
            case "baiduLotteryOcr":
                String filePath = "D:\\2.jpg";
                object = baiduCommonTextOCRService.commonImgOCR(filePath);
                break;
            case "tencentCosAuth":
                object = tencentCosAuthService.getTmpCosSecret();
                break;
            case "cosBucketFileList":
                object = tencentCosService.getBucketFileList(null, null, null, 1000);
                break;
            default:
        }
        logger.info("测试返回结果 [{}]", object.toString());
        return object;
    }

}
