package com.xwder.framework.utils.message;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName: Result
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:58:23
 * @Version: 1.0
 */

@Getter
@Setter
public class Result<T> {
    private Integer code;
    private String msg;
    private Object data;
}
