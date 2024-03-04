package com.easyauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.easyauth.domain.DTO.EmployeeDTO;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.VO.EmployeeVO;
import com.easyauth.domain.entity.Employee;

public interface EmployeeService extends IService<Employee> {
    void add(EmployeeDTO employeeDTO);

    void edit(EmployeeDTO employeeDTO);

    Page<EmployeeVO> getList(Integer current, Integer size);

    Page<EmployeeVO> conditionSearchWithOutRoleId(EmployeePageQueryDTO dto);

    Page<EmployeeVO> conditionSearchWithRoleId(EmployeePageQueryDTO dto);
}
