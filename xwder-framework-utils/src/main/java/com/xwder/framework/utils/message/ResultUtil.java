package com.xwder.framework.utils.message;


/**
 * @author xwder
 */
public class ResultUtil {

    /**
     * @param data
     * @return
     */
    public static Result success(Object data) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg("OK");
        result.setData(data);
        return result;
    }

    /**
     *
     * @param msg
     * @return
     */
    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(200);
        result.setMsg(msg);
        return result;
    }

    /**
     * @return
     */
    public static Result success() {
        return success(null);
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    /**
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        Result result = new Result();
        result.setCode(500);
        result.setMsg(msg);
        return result;
    }
}