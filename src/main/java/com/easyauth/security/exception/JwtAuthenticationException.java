package com.easyauth.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 无有效token
 */
public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
