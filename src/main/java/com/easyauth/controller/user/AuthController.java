package com.easyauth.controller.user;

import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.domain.DTO.UserFormLoginDTO;
import com.easyauth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "UserAuthController", description = "用户认证")
@RequestMapping("/user/auth")
public class AuthController {
    //TODO 完善员工/用户认证相关接口
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/formLogin")
    @Operation(summary = "表单登录", description = "返回token")
    public Result<String> formLogin(UserFormLoginDTO dto) {
        return Result.success(userService.formLogin(dto));
    }

}
