package com.easyauth.security.component;

import com.easyauth.common.constant.CodeConstant;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.security.exception.JwtAuthenticationException;
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

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtAuthenticationException {
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
        Claims claims = null;

        try {
            if (authToken != null) {
                claims = jwtUtil.parseJWT(authToken);
                log.info("用户已登入: {}", claims);
            } else {
                log.info("用户未登入");
                throw new JwtAuthenticationException(MessageConstant.UNAUTHORIZED);
            }
        } catch (Exception e) {
            throw new JwtAuthenticationException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }


}
