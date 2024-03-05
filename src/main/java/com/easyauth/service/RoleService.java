package com.easyauth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.DTO.RoleDTO;
import com.easyauth.domain.entity.Role;

public interface RoleService extends IService<Role>{
    void add(RoleDTO dto);

    void update(RoleDTO dto);
}
