package com.xwder.app.sysmodules.seo.service.intf;

/**
 * 网站seo
 *
 * @author xwder
 * @date 2021/1/20 9:45
 */
public interface SeoService {

    /**
     * 网站siteMap
     *
     * @return
     */
    String createSiteMapXmlContent(String domain);
}
