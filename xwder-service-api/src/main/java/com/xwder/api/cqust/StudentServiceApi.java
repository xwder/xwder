package com.xwder.api.cqust;

import com.xwder.api.cqust.fallback.StudentServiceFallbackFactory;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.message.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: xwder
 * @Date: 2019/7/13 14:36
 * @Description:
 */
@FeignClient(value = "XWDER-PROVIDER-CQUST", fallbackFactory = StudentServiceFallbackFactory.class)
public interface StudentServiceApi {

    @RequestMapping("/ky/student/page/{page}/{size}")
    public Result<KyStudent> listByPage(@PathVariable Integer page, @PathVariable Integer size,
                                        @RequestParam("sortField") final String sortField,
                                        @RequestParam("order") final Sort.Direction order);
}
