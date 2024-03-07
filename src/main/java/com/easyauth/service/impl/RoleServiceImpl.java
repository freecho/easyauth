package com.easyauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.domain.DTO.RoleDTO;
import com.easyauth.domain.entity.Role;
import com.easyauth.domain.entity.RolePermission;
import com.easyauth.mapper.RoleMapper;
import com.easyauth.service.RedisService;
import com.easyauth.service.ResourceService;
import com.easyauth.service.RolePermissionService;
import com.easyauth.service.RoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ResourceService resourceService;

    @Override
    @Transactional
    public void add(RoleDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        this.save(role);
        // 添加角色权限
        List<RolePermission> perms = new ArrayList<>();
        dto.getResourceIds().forEach(resourceId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setResourceId(resourceId);
            perms.add(rolePermission);
        });
        rolePermissionService.saveBatch(perms);
        // redis缓存设置
        resourceService.list().forEach(resource -> {
            redisService.set(role.getId() + ":" + resource.getId(), dto.getResourceIds().contains(resource.getId()));
        });

    }

    @Override
    @Transactional
    public void update(RoleDTO dto) {
        Role role = new Role();
        BeanUtils.copyProperties(dto, role);
        this.updateById(role);
        // 删除旧角色权限
        rolePermissionService.removeByRoleId(role.getId());
        // 添加新角色权限
        List<RolePermission> perms = new ArrayList<>();
        dto.getResourceIds().forEach(resourceId -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setResourceId(resourceId);
            perms.add(rolePermission);
        });
        rolePermissionService.saveBatch(perms);
        // 更新redis缓存
        resourceService.list().forEach(resource -> {
            redisService.set(role.getId() + ":" + resource.getId(), dto.getResourceIds().contains(resource.getId()));
        });

    }
}
