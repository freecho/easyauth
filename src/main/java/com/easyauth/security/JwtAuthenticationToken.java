package com.easyauth.security;

import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 自定义认证对象
 * 仅仅使用 identity和 id （解析自jwt）
 */
@Data
public class JwtAuthenticationToken implements Authentication {

    // 身份：user,employee
    private String identity;

    private String id;

    public JwtAuthenticationToken(String identity, String id) {
        this.identity = identity;
        this.id = id;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return null;
    }
}
