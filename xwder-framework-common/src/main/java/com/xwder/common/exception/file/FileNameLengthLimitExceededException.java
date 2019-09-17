package com.xwder.common.exception.file;

/**
 * @className: FileNameLengthLimitExceededException
 * @description: 文件名称超长限制异常类
 * @author: xwder
 * @Date: 2019-8-3 00:54:12
 * @Version: 1.1
 */
public class FileNameLengthLimitExceededException extends FileException {
    private static final long serialVersionUID = 1L;

    public FileNameLengthLimitExceededException(int defaultFileNameLength) {
        super("upload.filename.exceed.length", new Object[]{defaultFileNameLength});
    }
}