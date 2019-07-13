package com.xwder.api.cqust.fallback;

import com.xwder.api.cqust.StudentServiceApi;
import com.xwder.framework.domain.cqust.KyStudent;
import com.xwder.framework.utils.message.Result;
import feign.hystrix.FallbackFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

/**
 * @author xwder
 */
@Component
public class StudentServiceFallbackFactory implements FallbackFactory<StudentServiceApi> {
    @Override
    public StudentServiceApi create(Throwable throwable) {
        return new StudentServiceApi() {

            @Override
            public Result<KyStudent> listByPage(Integer page, Integer size, String sortField, Sort.Direction order) {
                return null;
            }
        };
    }
}
