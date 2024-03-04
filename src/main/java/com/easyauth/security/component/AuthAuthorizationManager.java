package com.easyauth.security.component;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.config.IgnoreUrlsConfig;
import com.easyauth.security.JwtAuthenticationToken;
import com.easyauth.security.exception.JwtAuthenticationException;
import com.easyauth.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.function.Supplier;

/**
 * 自定义授权管理器
 */
@Component
@Slf4j
public class AuthAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {
    @Autowired
    private RedisService redisService;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean inWhitelist = ignoreUrlsConfig.getUrls().stream().anyMatch(url -> antPathMatcher.match(url, request.getRequestURI()));
        if (inWhitelist) {
            return new AuthorizationDecision(true);
        }

        JwtAuthenticationToken authentication;
        try {
            authentication = (JwtAuthenticationToken) authenticationSupplier.get();
        } catch (Exception e) {
            // token为空
            log.info("token为空!!! {} URI: {}", inWhitelist, request.getRequestURI());
            throw new JwtAuthenticationException(MessageConstant.UNAUTHORIZED);
        }


        String userId = authentication.getId();
        String itentity = authentication.getIdentity();
        log.info("用户: {} 请求: {} 方法：{}", userId, request.getRequestURI(), request.getMethod());

        String resourceId = (String) redisService.get(request.getMethod() + ":" + request.getRequestURI());
        if (resourceId == null) {
            log.info("资源未找到: {}", request.getRequestURI());
            return new AuthorizationDecision(false);
        } else {
            //TODO 完善权限判断
            log.info("资源找到: {}", request.getRequestURI());


            return new AuthorizationDecision(true);
        }

    }
}
