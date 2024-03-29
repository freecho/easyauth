package com.easyauth.handler;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.exception.BaseException;
import com.easyauth.common.exception.BeanConvertException;
import com.easyauth.common.result.Result;
import com.easyauth.security.exception.JwtAuthenticationException;
import jakarta.mail.MessagingException;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.failed(e.getMessage());
    }

    /**
     * BeanUtils 拷贝source为空
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(IllegalArgumentException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.failed(MessageConstant.DARA_ERROR);
    }

    /**
     * 传参缺少必要部分导致空指针
     *
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(NullPointerException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.failed(MessageConstant.VALIDATE_FAILED);
    }

    /**
     * 请求错误
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(ServletException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.failed(MessageConstant.Bad_Request);
    }

    /**
     * 邮件发送异常
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(MessagingException e) {
        log.error("异常信息：{}", e.getMessage());
        return Result.failed(MessageConstant.UNKNOWN_ERROR);
    }
}
