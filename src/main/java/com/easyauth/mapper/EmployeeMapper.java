package com.easyauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    List<Employee> pageWithRolesId(@Param("dto") EmployeePageQueryDTO queryDTO);

    List<Long> selectRolesByEmployeeId(@Param("id") Long employeeId);
}
