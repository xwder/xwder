package com.xwder.app.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * @author xwder
 * @Description: html 处理工具类
 * @date 2020/7/15 0:08
 */
public class HtmlUtils {
    /**
     * html 转 字符串
     *
     * @param html
     * @return
     */
    public static String htmmConvertTxt(String html) {
        if (StrUtil.isEmpty(html)) {
            return "";
        }
        Document document = Jsoup.parse(html);
        Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);
        document.outputSettings(outputSettings);
        document.select("br").append("\\n");
        document.select("p").prepend("\\n");
        document.select("p").append("\\n");
        String newHtml = document.html().replaceAll("\\\\n", "\n");
        String plainText = Jsoup.clean(newHtml, "", Whitelist.none(), outputSettings);
        String result = StringEscapeUtils.unescapeHtml(plainText.trim());
        return result;
    }
}
