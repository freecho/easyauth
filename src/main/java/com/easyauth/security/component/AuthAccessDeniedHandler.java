package com.easyauth.security.component;

import com.easyauth.common.constant.CodeConstant;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.result.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 自定义结果：没有权限访问时
 */
@Component
public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        response.setStatus(CodeConstant.FORBIDDEN);

        Result<String> result = Result.failed(MessageConstant.FORBIDDEN);
        response.getWriter().println(objectMapper.writeValueAsString(result));
        response.getWriter().flush();

    }
}
