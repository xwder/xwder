package com.xwder.common.exception.file;

import com.xwder.common.exception.base.BaseException;

/**
 * @className: FileException
 * @description: 文件信息异常类
 * @author: xwder
 * @Date: 2019-8-3 00:53:41
 * @Version: 1.1
 */
public class FileException extends BaseException {
    private static final long serialVersionUID = 1L;

    public FileException(String code, Object[] args) {
        super("file", code, args, null);
    }

}