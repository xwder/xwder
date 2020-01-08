package com.xwder.biz.api.book;

import com.xwder.biz.api.book.config.BookInfoServiceFeignConfig;
import com.xwder.biz.api.book.fallback.BookInfoServiceFallbackFactory;
import com.xwder.cloud.commmon.api.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 图书模块api
 *
 * @author wande
 * @version 1.0
 * @date 2019/12/26
 */
@FeignClient(name = "xwder-biz-book", fallbackFactory = BookInfoServiceFallbackFactory.class, configuration = BookInfoServiceFeignConfig.class)
@RequestMapping(value = "/book")
public interface BookInfoServiceApi {

    /**
     * 分页查询书籍信息
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/bookinfos", method = RequestMethod.GET)
    CommonResult listBook(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize);


}