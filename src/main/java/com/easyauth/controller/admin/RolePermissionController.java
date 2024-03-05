package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.entity.RolePermission;
import com.easyauth.service.RedisService;
import com.easyauth.service.RolePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "角色权限")
@RequestMapping("/admin/rolePermission")
public class RolePermissionController {
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private RedisService redisService;

    @Operation(summary = "角色权限列表", description = "current必须提供，size默认为10")
    @GetMapping("/list")
    public Result<Page<RolePermission>> getList(Integer current, @RequestParam(required = false, defaultValue = "10") Integer size) {
        return Result.success(rolePermissionService.page(new Page<>(current, size)));
    }

    @PostMapping("/add")
    @Operation(summary = "添加角色权限")
    public Result<String> add(@RequestBody RolePermission rolePermission) {
        rolePermissionService.save(rolePermission);
        redisService.set(rolePermission.getRoleId() + ":" + rolePermission.getResourceId(), true);
        return Result.success();
    }

    @PutMapping("/delete")
    @Operation(summary = "删除角色权限")
    public Result<String> delete(RolePermission rolePermission) {
        rolePermissionService.delete(rolePermission);
        redisService.set(rolePermission.getRoleId() + ":" + rolePermission.getResourceId(),false);
        return Result.success();
    }


}
