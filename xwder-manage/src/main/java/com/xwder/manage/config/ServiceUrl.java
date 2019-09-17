package com.xwder.manage.config;

import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * eureka restApi 接口
 *
 * @Author: xwder
 * @Date: 2019/8/26 22:43
 * @Description:
 */
@RestController
public class ServiceUrl {

    @Autowired
    private EurekaClient eurekaClient;

    @GetMapping(value = "/manager/eurekaclient/infos")
    public Object serviceUrl() {
        return eurekaClient.getInstancesByVipAddress("XWDER-MANAGE", false);
    }


    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/book/discoveryclient/infos")
    public Object serviceUrl2() {
        return discoveryClient.getInstances("XWDER-SERVICE-BOOK");
    }

}
