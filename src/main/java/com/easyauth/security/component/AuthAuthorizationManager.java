package com.easyauth.security.component;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 自定义授权管理器
 */
@Component
public class AuthAuthorizationManager implements AuthorizationManager {


    @Override
    public AuthorizationDecision check(Supplier authentication, Object object) {
        return null;
    }

    @Override
    public void verify(Supplier authentication, Object object) {
        AuthorizationManager.super.verify(authentication, object);
    }
}
