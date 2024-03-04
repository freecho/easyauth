package com.easyauth.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * 用户详情 - redis 缓存使用
 */
@Data
public class UserDetail {
    private Integer id;
    private String username;
    private String email;
    private List<Integer> rolesId;
}
