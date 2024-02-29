package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.exception.InvalidDataException;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.entity.Role;
import com.easyauth.domain.entity.User;
import com.easyauth.domain.entity.UserRole;
import com.easyauth.mapper.UserMapper;
import com.easyauth.service.RoleService;
import com.easyauth.service.UserRoleService;
import com.easyauth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;


    public User getByUsername(String username) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username);
        return this.getOne(queryWrapper);
    }

    @Override
    public void switchStatus(Long id, Long status) {
        User user = this.getById(id);
        user.setStatus(status);
        this.updateById(user);
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());
        this.save(user);

        userDTO.getRoleIds().forEach(roleId -> {
            // 验证角色是否存在
            Role role = roleService.getById(roleId);
            if (role == null || role.getStatus() != 1) {
                throw new InvalidDataException(MessageConstant.VALIDATE_FAILED);
            }

            // 保存用户角色
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleService.save(userRole);
        });
    }

    @Override
    @Transactional
    public void edit(UserDTO userDTO) {
        //  更新用户信息
        User user = this.getById(userDTO.getId());

        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }
        if (userDTO.getStatus() != null) {
            user.setStatus(userDTO.getStatus());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }

        this.updateById(user);

        //  处理角色数据

        // 1. 删除初始的角色绑定数据
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, user.getId());
        userRoleService.remove(wrapper);
        wrapper.clear();

        // 2. 绑定新的角色
        userDTO.getRoleIds().forEach(roleId -> {
            // 验证目标角色是否存在
            Role role = roleService.getById(roleId);
            if (role == null || role.getStatus() != 1) {
                throw new InvalidDataException(MessageConstant.VALIDATE_FAILED);
            }

            // 保存用户角色
            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(roleId);
            userRoleService.save(userRole);
        });
    }
}
