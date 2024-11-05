package com.jinlink.common.exception.file;


import com.jinlink.common.exception.BaseException;

/**
 * 自定义文件异常
 */
public class FileException extends BaseException {

    public FileException(String code, Object[] args, String msg) {
        super("file", code, args, msg);
    }
}
