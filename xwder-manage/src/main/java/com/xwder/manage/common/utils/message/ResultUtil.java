package com.xwder.manage.common.utils.message;

/**
 * @ClassName: ResultUtil
 * @Description:
 * @Author: xwder
 * @Date: 2019年7月9日20:59:17
 * @Version: 1.0
 */
public class ResultUtil {
    /**
     * 成功且带数据
     **/
    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    /**
     * 成功但不带数据
     **/
    public static Result success() {
        return success(null);
    }

    /**
     * 失败
     **/
    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
