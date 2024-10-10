package com.jinlink.common.config;

import com.jinlink.common.api.Result;
import com.jinlink.common.api.ResultCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    //全局异常拦截
    @ExceptionHandler
    public Result<String> handlerException(Exception e) {
        e.printStackTrace();
        return Result.failure(ResultCode.FORBIDDEN.getCode(), e.getMessage());
    }
}
