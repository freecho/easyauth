package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.DTO.EmployeeDTO;
import com.easyauth.domain.entity.Employee;
import com.easyauth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工表")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Operation(summary = "添加员工")
    @PostMapping
    public Result<String> add(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.add(employeeDTO);
        return Result.success();
    }

    @Operation(summary = "编辑员工")
    @PutMapping
    public Result<String> edit(@RequestBody EmployeeDTO employeeDTO) {
        employeeService.edit(employeeDTO);
        return Result.success();
    }

    @Operation(summary = "员工列表")
    @GetMapping("/list")
    public Result<Page<Employee>> getList(int current, int size) {
        Page<Employee> page = new Page<>(current, size);
        Page<Employee> pageResult = employeeService.page(page);
        return Result.success(pageResult);
    }

    //TODO 员工条件查询

}
