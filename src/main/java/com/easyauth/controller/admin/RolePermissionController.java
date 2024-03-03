package com.easyauth.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


//TODO 实现相关的CURD操作 结合redis实现缓存
@RestController
@Tag(name = "角色权限")
@RequestMapping("/admin/rolePermission")
public class RolePermissionController {
}
