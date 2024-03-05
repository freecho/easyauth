package com.easyauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.entity.RolePermission;

public interface RolePermissionService extends IService<RolePermission> {
    void delete(RolePermission rolePermission);

    void removeByRoleId(Integer id);
}
