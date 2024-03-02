package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.exception.InvalidDataException;
import com.easyauth.common.utils.PageUtils;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.DTO.UserPageQueryDTO;
import com.easyauth.domain.VO.EmployeeVO;
import com.easyauth.domain.VO.UserVO;
import com.easyauth.domain.entity.*;
import com.easyauth.mapper.UserMapper;
import com.easyauth.service.RoleService;
import com.easyauth.service.UserRoleService;
import com.easyauth.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserMapper userMapper;


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

    @Override
    public Page<UserVO> conditionSearchWithOutRoleId(UserPageQueryDTO dto) {
        Page<User> page = new Page<>(dto.getCurrent(), dto.getSize());
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(dto.getUsername() != null, User::getUsername, dto.getUsername());
        wrapper.like(dto.getEmail() != null, User::getEmail, dto.getEmail());

        Page<User> pageResult = this.page(page, wrapper);

        List<UserRole> roleList = userRoleService.list();

        pageResult.getRecords().forEach(user -> {
            List<Long> roles = new ArrayList<>();

            roleList.forEach(userRole -> {
                if (userRole.getUserId().equals(user.getId())) {
                    roles.add(userRole.getRoleId());
                }
            });

            user.setRolesId(roles);
        });

        return PageUtils.convert(pageResult, UserVO.class);
    }

    public Page<UserVO> conditionSearchWithRoleId(UserPageQueryDTO dto) {
        List<User> userList = userMapper.pageWithRolesId(dto);
        int start = Math.min((int) ((dto.getCurrent() - 1) * dto.getSize()), userList.size());
        int end = Math.min((int) (start + dto.getSize()), userList.size());
        userList.subList(start, end);

        Page<User> page = new Page<>(dto.getCurrent(), dto.getSize());
        page.setRecords(userList);
        page.setTotal(userList.size());

        return PageUtils.convert(page, UserVO.class);
    }

    @Override
    public Page<UserVO> getList(Long current, Long size) {
        Page<User> page = new Page<>(current, size);
        Page<User> pageResult = this.page(page);

        List<UserRole> roleList = userRoleService.list();

        pageResult.getRecords().forEach(user -> {
            List<Long> roles = new ArrayList<>();

            roleList.forEach(role -> {
                if (role.getUserId().equals(user.getId())) {
                    roles.add(role.getRoleId());
                }
            });

            user.setRolesId(roles);
        });

        return PageUtils.convert(pageResult, UserVO.class);
    }
}
