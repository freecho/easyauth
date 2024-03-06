package com.easyauth.security.component;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.config.IgnoreUrlsConfig;
import com.easyauth.domain.entity.UserDetail;
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
            throw new JwtAuthenticationException(MessageConstant.UNAUTHORIZED);
        }

        Integer userId = authentication.getId();
        String identity = authentication.getIdentity();

        Integer resourceId = (Integer) redisService.get(request.getMethod() + ":" + request.getRequestURI());
        if (resourceId == null) {
            return new AuthorizationDecision(false);
        } else {
            // 从redis中获取用户信息 identity:userId
            UserDetail userDetail = (UserDetail) redisService.get(identity + ":" + userId);
            // 判断用户是否有权限 redis 查找 roleId:resourceId
            boolean hasAuthority = userDetail.getRolesId().stream().anyMatch(roleId -> (boolean) redisService.get(roleId + ":" + resourceId));

            if (!hasAuthority) {
                log.info("{}: {} 无权限访问: {}", identity, userId, request.getRequestURI());
                return new AuthorizationDecision(false);
            }
        }

        return new AuthorizationDecision(true);
    }

}

