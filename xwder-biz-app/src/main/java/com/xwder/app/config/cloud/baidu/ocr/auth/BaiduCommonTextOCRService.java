package com.xwder.app.config.cloud.baidu.ocr.auth;

import com.xwder.app.config.cloud.baidu.ocr.util.Base64Util;
import com.xwder.app.config.cloud.baidu.ocr.util.FileUtil;
import com.xwder.app.config.cloud.baidu.ocr.util.HttpUtil;
import com.xwder.app.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;

/**
 * https://cloud.baidu.com/doc/OCR/s/zk3h7xz52
 * 通用文字识别
 */
@Service
public class BaiduCommonTextOCRService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private String serviceName = "百度通用文字识别";

    @Autowired
    private BaiduAuthService baiduAuthService;

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     *
     * {
     *     "log_id":6612987156857423138,
     *     "words_result_num":6,
     *     "words_result":[
     *         {
     *             "words":"050610112233-11"
     *         },
     *         {
     *             "words":"開開開開輯開"
     *         },
     *         {
     *             "words":"010622232829-06"
     *         },
     *         {
     *             "words":"030510152132-08"
     *         },
     *         {
     *             "words":"030809141926-02"
     *         },
     *         {
     *             "words":"071317182429-08"
     *         }
     *     ]
     * }
     */
    public String commonImgOCR(String imgPath) {
        Long startTime = System.currentTimeMillis();
        logger.info("[{}]开始,图片路径[{}]", serviceName, imgPath);
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
        String result = null;
        try {
            byte[] imgData = FileUtil.readFileByBytes(imgPath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam + "&";
            // 先获取baiduOcrAccessToken
            String baiduOcrAccessToken = baiduAuthService.getApiAuth();
            if (baiduOcrAccessToken == null) {
                logger.error("[{}]结束,获取百度ocr access_token,耗时[{}]", serviceName, DateUtil.diffTime(startTime, System.currentTimeMillis()));
                return "";
            }
            logger.info("[{}]开始,获取accessToken[{}]", serviceName, baiduOcrAccessToken);
            result = HttpUtil.post(url, baiduOcrAccessToken, param);
            logger.info("[{}]开始,请求结果[{}]", serviceName, result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("[{]]发生错误，错误信息[{}]", serviceName, e);
        }
        logger.info("[{}]结束,耗时[{}]", serviceName, DateUtil.diffTime(startTime, System.currentTimeMillis()));
        return result;
    }

}