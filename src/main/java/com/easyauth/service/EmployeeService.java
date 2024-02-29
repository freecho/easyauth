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

    Page<EmployeeVO> conditionSearch(EmployeePageQueryDTO dto);

    Page<EmployeeVO> getList(Long current, Long size);
}
