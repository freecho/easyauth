package com.easyauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.domain.entity.EmployeeRole;
import com.easyauth.mapper.EmployeeRoleMapper;
import com.easyauth.service.EmployeeRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeRoleServiceImpl extends ServiceImpl<EmployeeRoleMapper, EmployeeRole> implements EmployeeRoleService {
    /**
     * 根据员工id查询角色列表
     *
     * @param id
     * @return
     */
    @Override
    public List<EmployeeRole> list(Long id) {
        LambdaQueryWrapper<EmployeeRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EmployeeRole::getEmployeeId, id);

        List<EmployeeRole> roleList = this.list(wrapper);

        return null;
    }
}
