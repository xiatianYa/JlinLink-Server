package com.jinlink.modules.monitor.aspect;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import com.alibaba.fastjson2.JSON;
import com.jinlink.common.constants.RequestConstant;
import com.jinlink.common.util.IPUtils;
import com.jinlink.modules.monitor.entity.MonLogsError;
import com.jinlink.modules.monitor.entity.MonLogsOperation;
import com.jinlink.modules.monitor.entity.dto.MonLogsOperationAddDTO;
import com.jinlink.modules.monitor.service.MonLogsErrorService;
import com.jinlink.modules.monitor.service.MonLogsOperationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.aspectj.lang.annotation.*;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

/**
 * 操作/错误异常日志切面
 */

@Slf4j
@Aspect
@Component
public class OperationLogAspect {

    /**
     * 操作日志业务处理对象
     */
    private final ThreadLocal<MonLogsOperationAddDTO> logsOperationAddDTO = new ThreadLocal<>();

    /**
     * 定义一个开始时间
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Resource
    private MonLogsOperationService monLogsOperationService;

    @Resource
    private MonLogsErrorService monLogsErrorService;

    /**
     * 定义切入点
     */
    @Pointcut("execution(* com.jinlink.controller.*.*Controller.*(..))")
    public void controllerPoint() {
    }

    @SneakyThrows
    @Before("controllerPoint()")
    public void beforeLog(JoinPoint point) {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        // 获取 request 信息
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();
        // 获取 请求方法 Method
        String requestMethod = request.getMethod();
        // 如果是 GET 或者 OPTIONS 请求，直接返回
        if ("OPTIONS".equals(requestMethod) || "GET".equals(requestMethod)) {
            return;
        }
        // 获取 contentType
        String contentType = request.getHeader(RequestConstant.CONTENT_TYPE_NAME);
        String requestId = request.getHeader(RequestConstant.REQUEST_ID);
        // 获取请求 URI
        String requestURI = request.getRequestURI();
        MethodSignature ms = (MethodSignature) point.getSignature();
        // 获取方法上的注解
        Operation operation = ms.getMethod().getDeclaredAnnotation(Operation.class);
        // 获取请求参数
        Object[] args = point.getArgs();
        // 将参数所在的数组转换成json
        List<String> arguments = new ArrayList<>();
        // 过滤报异常的参数
        for (Object arg : args) {
            if (arg instanceof ServletRequest || arg instanceof ServletResponse || arg instanceof MultipartFile || arg instanceof MultipartFile[]) {
                continue;
            }
            arguments.add(JSON.toJSONString(arg));
        }
        String ip = JakartaServletUtil.getClientIP(request);
        logsOperationAddDTO.set(MonLogsOperationAddDTO.builder()
                .requestId(requestId)
                .requestUri(requestURI)
                .requestMethod(requestMethod)
                .methodName(ms.getName())
                .operation(operation.summary())
                .contentType(contentType)
                .methodParams(JSON.toJSONString(arguments))
                .ip(ip)
                .ipAddr(IPUtils.getIpAddr(ip))
                .userAgent(request.getHeader(RequestConstant.USER_AGENT))
                .build());
    }

    /**
     * 后置操作
     */
    @AfterReturning(pointcut = "controllerPoint()", returning = "result")
    public void afterReturning(Object result) {
        try {
            MonLogsOperationAddDTO addDTO = logsOperationAddDTO.get();
            if (ObjectUtils.isNotEmpty(addDTO)) {
                MonLogsOperation monLogsOperation = BeanUtil.copyProperties(addDTO, MonLogsOperation.class);
                monLogsOperation.setUseTime(System.currentTimeMillis() - startTime.get());
                monLogsOperationService.save(monLogsOperation);
            }
        } finally {
            remove();
        }
    }

    /**
     * 异常 方法
     */
    @AfterThrowing(pointcut = "controllerPoint()", throwing = "exception")
    public void afterThrowing(JoinPoint joinPoint, Throwable exception) {
        try {
            MonLogsOperationAddDTO addDTO = logsOperationAddDTO.get();
            if (ObjectUtils.isNotEmpty(addDTO)) {
                MonLogsError monLogsError = BeanUtil.copyProperties(addDTO, MonLogsError.class);
                StackTraceElement stackTraceElement = exception.getStackTrace()[0];
                monLogsError.setLine(stackTraceElement.getLineNumber());
                monLogsError.setExceptionMessage(exception.getMessage());
                monLogsError.setExceptionClass(stackTraceElement.getClassName());
                monLogsError.setStackTrace(Matcher.quoteReplacement(Arrays.toString(exception.getStackTrace())));
                monLogsError.setUseTime(System.currentTimeMillis() - startTime.get());
                monLogsErrorService.save(monLogsError);
            }
        } finally {
            remove();
        }
    }

    private void remove() {
        logsOperationAddDTO.remove();
        startTime.remove();
    }
}
