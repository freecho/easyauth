package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.entity.Role;
import com.easyauth.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/role")
@Tag(name = "角色管理")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Operation(summary = "添加角色")
    @PostMapping
    public Result<String> add(Role role) {
        roleService.save(role);
        return Result.success();
    }

    @Operation(summary = "修改角色")
    @PutMapping
    public Result<String> update(Role role) {
        roleService.updateById(role);
        return Result.success();
    }

    @Operation(summary = "角色列表")
    @GetMapping("/list")
    public Result<Page<Role>> getList(int current, @RequestParam(required = false, defaultValue = "10") int size) {
        Page<Role> page = new Page<>(current, size);
        Page<Role> pageResult = roleService.page(page);
        return Result.success(pageResult);
    }


}
