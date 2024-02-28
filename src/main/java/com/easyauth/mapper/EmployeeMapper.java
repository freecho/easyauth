package com.easyauth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.easyauth.domain.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
