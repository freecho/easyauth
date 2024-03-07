package com.easyauth.controller.user;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.domain.DTO.*;
import com.easyauth.service.EmailService;
import com.easyauth.service.RedisService;
import com.easyauth.service.UserService;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("userAuthController")
@Tag(name = "用户认证", description = "用户认证")
@RequestMapping("/user/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private EmailService emailService;

    @PostMapping("/formLogin")
    @Operation(summary = "表单登录", description = "返回token")
    public Result<String> formLogin(@RequestBody UserFormLoginDTO dto) {
        return Result.success(userService.formLogin(dto));
    }

    @PostMapping("/logout")
    @Operation(summary = "登出", description = "前端自行删除token，后端清理缓存")
    public Result<String> logout(@RequestBody TokenDTO dto) {
        Claims claims = jwtUtil.parseJWT(dto.getToken());
        String identity = claims.get("identity", String.class);
        Integer id = claims.get("id", Integer.class);
        redisService.del(identity + ":" + id);
        return Result.success();
    }

    @PostMapping("/register")
    @Operation(summary = "注册", description = "注册用户,自动登入返回token（需要先调用/registerEmailCode接口）")
    public Result<String> register(@RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/registerEmailCode")
    @Operation(summary = "发送验证码", description = "注册时验证发送验证码邮件")
    public Result<String> registerSendCode(@RequestBody UserVerifyDTO dto) throws MessagingException {
        // 验证用户名，邮箱是否唯一
        if (userService.isExist(dto.getUsername(), dto.getEmail())) {
            return Result.failed(MessageConstant.Username_Or_Email_Exist);
        }
        emailService.sendVerifyCode(dto.getEmail());
        return Result.success();
    }

    @PostMapping("/forgetPassword")
    @Operation(summary = "忘记密码", description = "根据邮箱找回密码（需要先调用/forgetPasswordEmailCode接口）")
    public Result<String> forgetPassword(@RequestBody ForgetPasswordDTO dto) {
        return userService.forgetPassword(dto);
    }

    @PostMapping("/forgetPasswordEmailCode")
    @Operation(summary = "发送验证码", description = "忘记密码时验证发送验证码邮件")
    public Result<String> forgetPasswordSendCode(@RequestBody ForgetPasswordVerifyDTO dto) throws MessagingException {
        if (!userService.isExist(dto.getUsername(), dto.getEmail())) {
            return Result.failed(MessageConstant.Username_Or_Email_Not_Match);
        }
        emailService.sendVerifyCode(dto.getEmail());
        return Result.success();
    }

    @PostMapping("/changePassword")
    @Operation(summary = "修改密码", description = "需要提交token，旧密码，新密码")
    public Result<String> changePassword(@RequestBody ChangePasswordDTO dto) {
        return userService.changePassword(dto);
    }

}
