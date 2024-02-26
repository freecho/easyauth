package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.easyauth.common.result.Result;
import com.easyauth.domain.entity.User;
import com.easyauth.mapper.UserMapper;
import com.easyauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "根据用户名获取用户")
    @GetMapping("/{username}")
    public Result<User> getByUsername(@PathVariable String username) {
        User user = userService.getByUsername(username);
        return Result.success(user);
    }

    @Operation(summary = "添加用户")
    @PostMapping
    public Result<String> save(@RequestBody User user) {
        userService.save(user);
        return Result.success();
    }

    @Operation(summary = "编辑用户")
    @PutMapping
    public Result<String> update(@RequestBody User user) {
        userService.updateById(user);
        return Result.success();
    }

    @Operation(summary = "切换用户状态")
    @PutMapping("/status/{status}")
    public Result<String> switchStatus(@RequestParam Long id, @PathVariable Integer status) {
        userService.switchStatus(id, status);
        return Result.success();
    }

}
