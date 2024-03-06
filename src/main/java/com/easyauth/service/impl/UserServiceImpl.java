package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.constant.NumberConstant;
import com.easyauth.common.exception.InvalidDataException;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.common.utils.PageUtil;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.DTO.UserFormLoginDTO;
import com.easyauth.domain.DTO.UserPageQueryDTO;
import com.easyauth.domain.DTO.UserRegisterDTO;
import com.easyauth.domain.VO.UserVO;
import com.easyauth.domain.entity.*;
import com.easyauth.mapper.UserMapper;
import com.easyauth.service.RedisService;
import com.easyauth.service.RoleService;
import com.easyauth.service.UserRoleService;
import com.easyauth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisService redisService;


    @Override
    public void switchStatus(Integer id, Integer status) {
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
            List<Integer> roles = new ArrayList<>();

            roleList.forEach(userRole -> {
                if (userRole.getUserId().equals(user.getId())) {
                    roles.add(userRole.getRoleId());
                }
            });

            user.setRolesId(roles);
        });

        return PageUtil.convert(pageResult, UserVO.class);
    }

    public Page<UserVO> conditionSearchWithRoleId(UserPageQueryDTO dto) {
        List<User> userList = userMapper.pageWithRolesId(dto);
        int start = Math.min((int) ((dto.getCurrent() - 1) * dto.getSize()), userList.size());
        int end = Math.min((int) (start + dto.getSize()), userList.size());
        userList.subList(start, end);

        Page<User> page = new Page<>(dto.getCurrent(), dto.getSize());
        page.setRecords(userList);
        page.setTotal(userList.size());

        return PageUtil.convert(page, UserVO.class);
    }

    @Override
    public Page<UserVO> getList(Integer current, Integer size) {
        Page<User> page = new Page<>(current, size);
        Page<User> pageResult = this.page(page);

        List<UserRole> roleList = userRoleService.list();

        pageResult.getRecords().forEach(user -> {
            List<Integer> roles = new ArrayList<>();

            roleList.forEach(role -> {
                if (role.getUserId().equals(user.getId())) {
                    roles.add(role.getRoleId());
                }
            });

            user.setRolesId(roles);
        });

        return PageUtil.convert(pageResult, UserVO.class);
    }

    @Override
    public String formLogin(UserFormLoginDTO dto) {
        // 验证数据
        if (dto == null || dto.getUsername() == null || dto.getPassword() == null) {
            throw new InvalidDataException(MessageConstant.VALIDATE_FAILED);
        }

        User user = this.getOne(new LambdaQueryWrapper<User>().eq(User::getUsername, dto.getUsername()));
        if (user == null || !passwordEncoder.matches(dto.getPassword(), user.getPassword()) || user.getStatus() != 1) {
            throw new InvalidDataException(MessageConstant.Login_Error);
        }
        // redis中存储用户信息
        UserDetail userDetail = new UserDetail();
        BeanUtils.copyProperties(user, userDetail);
        List<Integer> rolesId = userRoleService.list(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId,
                user.getId())).stream().map(UserRole::getRoleId).toList();
        userDetail.setRolesId(rolesId);
        redisService.set("user:" + user.getId(), userDetail, NumberConstant.Week_Seconds);

        // 生成token
        Map<String, Object> map = new HashMap<>();
        map.put("identity", "user");
        map.put("id", user.getId());
        return jwtUtil.createJWT(map);
    }

    @Override
    public String register(UserRegisterDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 默认角色
        user.setRolesId(new ArrayList<Integer>(4));
        this.save(user);
        // 调用登录接口
        UserFormLoginDTO userFormLoginDTO = new UserFormLoginDTO();
        userFormLoginDTO.setUsername(dto.getUsername());
        userFormLoginDTO.setPassword(dto.getPassword());
        return this.formLogin(userFormLoginDTO);
    }
}
