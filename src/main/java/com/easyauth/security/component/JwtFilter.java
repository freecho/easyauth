package com.easyauth.security.component;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.config.IgnoreUrlsConfig;
import com.easyauth.security.JwtAuthenticationToken;
import com.easyauth.security.exception.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
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

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, JwtAuthenticationException {
        // 放行OPTIONS请求
        if (request.getMethod().equals(HttpMethod.OPTIONS.toString())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 放行认证请求
        String path = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean inWhitelist = ignoreUrlsConfig.getUrls().stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
        if (inWhitelist) {
            filterChain.doFilter(request, response);
            return;
        }


        String authToken = request.getHeader(tokenHeader);
        Claims claims;

        try {
            if (authToken != null) {
                claims = jwtUtil.parseJWT(authToken);
                // 获取用户身份
                String id = claims.get("id", String.class);
                String identity = claims.get("identity", String.class);
                JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(identity, id);
                // 设置用户身份
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            } else {
                throw new JwtAuthenticationException(MessageConstant.UNAUTHORIZED);
            }
        } catch (Exception e) {
            if (e instanceof JwtException)
                throw new JwtAuthenticationException(MessageConstant.UNAUTHORIZED);
            throw new JwtAuthenticationException(e.getMessage());
        }

        filterChain.doFilter(request, response);
    }


}
