package com.xwder.common.exception.file;

/**
 * @className: FileSizeLimitExceededException
 * @description: 文件名大小限制异常类
 * @author: xwder
 * @Date: 2019-8-3 00:57:18
 * @Version: 1.1
 */
public class FileSizeLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileSizeLimitExceededException(long defaultMaxSize) {
        super("upload.exceed.maxSize", new Object[]{defaultMaxSize});
    }
}