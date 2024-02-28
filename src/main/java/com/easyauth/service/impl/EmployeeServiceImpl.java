package com.easyauth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.easyauth.domain.entity.Employee;
import com.easyauth.mapper.EmployeeMapper;
import com.easyauth.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
