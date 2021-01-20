package com.xwder.app.sysmodules.seo.service;

import com.xwder.app.sysmodules.seo.service.intf.SeoService;
import com.xwder.app.utils.DateUtil;
import com.xwder.app.utils.HttpServletRequestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * 网站siteMap
 *
 * @author xwder
 * @date 2021/1/20 10:27
 */
@Slf4j
@Controller
public class SiteMapController {

    @Autowired
    private SeoService SeoService;

    @GetMapping("/sitemap.xml")
    public void createSiteMapXml(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long startTime = System.currentTimeMillis();
        response.setContentType(MediaType.APPLICATION_XML_VALUE);
        response.setCharacterEncoding("UTF-8");
        Writer writer = response.getWriter();
        String tempContextUrl = HttpServletRequestUtil.getDomain(request);
        String siteMapXmlContent = SeoService.createSiteMapXmlContent(tempContextUrl);
        writer.append(siteMapXmlContent);
        log.info("生成网站siteMap.xml耗时：{}", DateUtil.diffTime(startTime,System.currentTimeMillis()));
    }
}
