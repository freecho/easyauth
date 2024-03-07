package com.easyauth;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.easyauth.common.result.Result;
import com.easyauth.common.utils.JwtUtil;
import com.easyauth.controller.admin.ResourceController;
import com.easyauth.controller.admin.RoleController;
import com.easyauth.controller.admin.RolePermissionController;
import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.entity.*;
import com.easyauth.mapper.EmployeeMapper;
import com.easyauth.security.JwtAuthenticationToken;
import com.easyauth.service.RedisService;
import com.easyauth.service.ResourceService;
import io.jsonwebtoken.Claims;
import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.*;


@SpringBootTest
class EasyauthApplicationTests {

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleController roleController;

    @Autowired
    private RolePermissionController rolePermissionController;

    @Autowired
    private ResourceController resourceController;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ImageCaptchaApplication application;


    @Test
    @Async
    void contextLoads() throws MessagingException {
        // 解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
        System.getProperties().setProperty("mail.mime.address.usecanonicalhostname", "false");
        // 获取 MimeMessage
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        Session session = mimeMessage.getSession();
        // 设置 日志打印控制器
        session.setDebug(true);
        //  解决本地DNS未配置 ip->域名场景下，邮件发送太慢的问题
        session.getProperties().setProperty("mail.smtp.localhost", "myComputer");

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo("2507544221@qq.com");
        helper.setFrom("freecho@qq.com");
        helper.setSubject("主题：HTML邮件");
        helper.setText("<html><body><h1>Hello World!</h1></body></html>", true);
        mailSender.send(message);


    }


}
