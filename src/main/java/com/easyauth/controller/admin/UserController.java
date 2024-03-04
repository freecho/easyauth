package com.easyauth.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.domain.DTO.UserDTO;
import com.easyauth.domain.DTO.UserPageQueryDTO;
import com.easyauth.domain.VO.UserVO;
import com.easyauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user")
@Tag(name = "用户管理")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "用户分页条件查询", description = "current必须提供，size默认为10")
    @GetMapping("/conditionSearch")
    public Result<Page<UserVO>> conditionSearch(UserPageQueryDTO dto) {
        if (dto.getRoleIds() == null || dto.getRoleIds().isEmpty())
            return Result.success(userService.conditionSearchWithOutRoleId(dto));
        return Result.success(userService.conditionSearchWithRoleId(dto));
    }

    @Operation(summary = "添加用户")
    @PostMapping
    public Result<String> add(@RequestBody UserDTO userDTO) {
        userService.add(userDTO);
        return Result.success();
    }

    @Operation(summary = "编辑用户")
    @PutMapping
    public Result<String> edit(@RequestBody UserDTO userDTO) {
        userService.edit(userDTO);
        return Result.success();
    }

    /**
     * 切换用户状态，用户拓展封禁，冻结等功能
     *
     * @param id
     * @param status
     * @return
     */
    @Operation(summary = "切换用户状态")
    @PutMapping("/status")
    public Result<String> switchStatus(Long id, Long status) {
        userService.switchStatus(id, status);
        return Result.success();
    }

    @Operation(summary = "用户列表", description = "current必须提供，size默认为10")
    @GetMapping("/list")
    public Result<Page<UserVO>> getList(Long current, @RequestParam(required = false, defaultValue = "10") Long size) {
        return Result.success(userService.getList(current, size));
    }

}
