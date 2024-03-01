package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.PageUtils;
import com.easyauth.domain.DTO.EmployeeDTO;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.VO.EmployeeVO;
import com.easyauth.domain.entity.Employee;
import com.easyauth.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工表")
@Slf4j
public class EmployeeController {
    //TODO 动态条件查询增加角色字段
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
    public Result<Page<EmployeeVO>> getList(Long current, Long size) {
        return Result.success(employeeService.getList(current, size));
    }

    @Operation(summary = "员工分页条件查询", description = "current必须提供，size默认为10")
    @GetMapping("/conditionSearch")
    public Result<Page<EmployeeVO>> conditionSearch(EmployeePageQueryDTO dto) {
        return Result.success(employeeService.conditionSearch(dto));
    }


}
