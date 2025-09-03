package com.wong.question.config;

import com.wong.question.utils.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice  // 统一异常处理
public class GlobalExceptionHandlerConfig {

    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        // 捕获 RuntimeException，返回前端
        e.printStackTrace();
        return R.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        e.printStackTrace(); // 方便调试，生产环境可以打日志
        return R.error("服务器内部错误");
    }
}