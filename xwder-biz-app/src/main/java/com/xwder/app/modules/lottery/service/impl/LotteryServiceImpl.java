package com.xwder.app.modules.lottery.service.impl;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.xwder.app.config.cloud.baidu.ocr.auth.BaiduCommonTextOCRService;
import com.xwder.app.modules.lottery.service.intf.LotteryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wande
 * @version 1.0
 * @date 2020/07/08
 */
@Service
public class LotteryServiceImpl implements LotteryService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BaiduCommonTextOCRService baiduCommonTextOCRService;


    /**
     * lottery 百度ocr通用文字识别
     *
     * @param lotteryFile
     * @return 返回识别的字符串
     */
    @Override
    public List<String> lotteryUpAndOcr(String lotteryFile) {
        String ocrResult = baiduCommonTextOCRService.commonImgOCR(lotteryFile);
        JSONObject ocrResultjsonObject = JSONUtil.parseObj(ocrResult);
        Integer wordResultNum = ocrResultjsonObject.getInt("words_result_num");
        JSONArray ocrResultjsonObjectJSONArray = ocrResultjsonObject.getJSONArray("words_result");

        List<String> numberList = new ArrayList<>(6);
        for (int i = 0; i < wordResultNum; i++) {
            JSONObject wordsJsonObject = (JSONObject) ocrResultjsonObjectJSONArray.get(i);
            String words = wordsJsonObject.getStr("words");
            logger.info("本次识别返回的words-{}: [{}] ,文件地址：[{}]", i + 1, words, lotteryFile);
            String handleWords = ocrResultSpecialChar(words);
            logger.info("特殊字符处理过后的words-{}: [{}] ,文件地址：[{}]", i + 1, handleWords, lotteryFile);
            if (handleWords.length() == 14 && NumberUtil.isLong(handleWords)) {
                numberList.add(handleWords);
            }
        }
        return numberList;
    }

    /**
     * 处理ocr特殊字符 能处理的就处理了
     *
     * @param words
     * @return
     */
    private String ocrResultSpecialChar(String words) {
        String result = words.replace("|", "").replaceAll("-", "");
        return result;
    }
}
