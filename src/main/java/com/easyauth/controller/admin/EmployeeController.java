package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.entity.Employee;
import com.easyauth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工表")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    //TODO 完善员工  注意权限相关的操作

    @Operation(summary = "员工列表")
    @GetMapping("/list")
    public Result<Page<Employee>> getList(int current, int size) {
        Page<Employee> page = new Page<>(current, size);
        Page<Employee> pageResult = employeeService.page(page);
        return Result.success(pageResult);
    }


}
