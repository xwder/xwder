package com.xwder.biz.app.modules.lottery.service.intf;

import java.util.List;

/**
 * lotteryService
 */
public interface LotteryService {

    /**
     * lottery 百度ocr通用文字识别
     * @param lotteryFile
     * @return 返回识别的字符串
     */
    List<String> lotteryUpAndOcr(String lotteryFile);

}
