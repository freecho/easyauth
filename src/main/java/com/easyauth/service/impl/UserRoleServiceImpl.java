package com.easyauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.domain.entity.UserRole;
import com.easyauth.mapper.UserRoleMapper;
import com.easyauth.service.UserRoleService;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
}
