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
        return Result.builder().code(200).msg("").data(data).build();
    }

    /**
     * @param msg
     * @return
     */
    public static Result success(String msg) {
        return Result.builder().code(200).msg(msg).build();
    }

    /**
     * @return
     */
    public static Result success() {
        return success("");
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    public static Result error(int code, String msg) {
        return Result.builder().code(code).msg(msg).build();
    }

    /**
     * @param msg
     * @return
     */
    public static Result error(String msg) {
        return Result.builder().code(500).msg(msg).build();
    }

    /**
     * @param code
     * @param msg
     * @return
     */
    public static Result result(int code, String msg) {
        return Result.builder().code(code).msg(msg).build();
    }
}