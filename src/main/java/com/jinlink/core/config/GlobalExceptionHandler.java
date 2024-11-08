package com.jinlink.core.config;

import com.jinlink.common.api.Result;
import com.jinlink.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局鉴权异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    //全局异常拦截
    @ExceptionHandler
    public Result<String> handlerException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.failure(ResultCode.FORBIDDEN.getCode(), e.getMessage());
    }
}
