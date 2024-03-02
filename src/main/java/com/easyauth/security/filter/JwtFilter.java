package com.easyauth.security.filter;

import com.easyauth.common.constant.CodeConstant;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT登录授权过滤器
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //TODO 完善路径的判断

        // 放行OPTIONS请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 临时使用
        String path = request.getRequestURI();
        if (path.contains("/admin") || path.contains("/user")) {
            // 放行
            filterChain.doFilter(request, response);
            return;
        }


        String authToken = request.getHeader(tokenHeader);
        if (authToken != null) {
            Claims claims = jwtUtil.parseJWT(authToken);
            log.info("用户已登入: {}", claims);
            // 放行
            filterChain.doFilter(request, response);
        } else {
            // 未登录

            // 拦截请求并返回未登录信息
            response.setContentType("application/json;charset=UTF-8");

            response.setStatus(CodeConstant.UNAUTHORIZED);

            Result<String> result = Result.failed(MessageConstant.USER_NOT_LOGIN);

            response.getWriter().write(objectMapper.writeValueAsString(result));

        }


    }


}
