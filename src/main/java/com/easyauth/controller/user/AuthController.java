package com.easyauth.controller.user;

import com.easyauth.common.constant.MessageConstant;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.domain.DTO.TokenDTO;
import com.easyauth.domain.DTO.UserFormLoginDTO;
import com.easyauth.domain.DTO.UserRegisterDTO;
import com.easyauth.domain.DTO.UserVerifyDTO;
import com.easyauth.domain.entity.User;
import com.easyauth.service.EmailService;
import com.easyauth.service.RedisService;
import com.easyauth.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@Tag(name = "UserAuthController", description = "用户认证")
@RequestMapping("/user/auth")
public class AuthController {
    //TODO 完善员工/用户认证相关接口
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
    @Operation(summary = "注册", description = "注册用户,自动登入返回token")
    public Result<String> register(@RequestBody UserRegisterDTO dto) {
        return userService.register(dto);
    }

    @PostMapping("/emailCode")
    @Operation(summary = "发送验证码", description = "发送验证码")
    public Result<String> sendCode(@RequestBody UserVerifyDTO dto) throws MessagingException {
        // 验证用户名，邮箱是否唯一
        if (userService.isExist(dto.getUsername(), dto.getEmail())) {
            return Result.failed(MessageConstant.Username_Or_Email_Exist);
        }
        emailService.sendVerifyCode(dto.getEmail());
        return Result.success();
    }


}
