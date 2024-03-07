package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.DTO.EmployeeDTO;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.VO.EmployeeVO;
import com.easyauth.service.EmployeeService;
import com.easyauth.service.RedisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/employee")
@Tag(name = "员工管理")
@Slf4j
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private RedisService redisService;

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
        redisService.del("employee:" + employeeDTO.getId());
        return Result.success();
    }

    @Operation(summary = "员工列表")
    @GetMapping("/list")
    public Result<Page<EmployeeVO>> getList(Integer current,@RequestParam(required = false,defaultValue = "10") Integer size) {
        return Result.success(employeeService.getList(current, size));
    }

    @Operation(summary = "员工分页条件查询", description = "current必须提供，size默认为10")
    @GetMapping("/conditionSearch")
    public Result<Page<EmployeeVO>> conditionSearch(EmployeePageQueryDTO dto) {
        //  如果没有角色id，分开查询两个表即可
        if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty())
            return Result.success(employeeService.conditionSearchWithOutRoleId(dto));
        //  如果有角色id，使用连表查询
        return Result.success(employeeService.conditionSearchWithRoleId(dto));
    }

}
