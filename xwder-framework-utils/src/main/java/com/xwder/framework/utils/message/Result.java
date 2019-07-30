package com.xwder.framework.utils.message;

import lombok.*;

/**
 * @ClassName: Result
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:58:23
 * @Version: 1.0
 */

@Getter
@Setter
@ToString
public class Result<T> {
    private Integer code;
    private String msg;
    private Object data;
}
