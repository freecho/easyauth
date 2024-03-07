package com.easyauth.service.impl;

import com.easyauth.common.constant.NumberConstant;
import com.easyauth.service.EmailService;
import com.easyauth.service.RedisService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailServiceImpl implements EmailService {
    @Value("${spring.mail.username}")
    private String from;
    @Value("${config.sitename}")
    private String siteName;

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private RedisService redisService;
    @Autowired
    private TemplateEngine templateEngine;

    private String verifyCode() {
        return String.valueOf((int) ((Math.random() * 9 + 1) * 1000));
    }

    @Override
    public void sendVerifyCode(String to) throws MessagingException {
        // 生成验证码
        String code = this.verifyCode();
        redisService.set("email:" + to, code, NumberConstant.Ten_Minutes_Seconds);

        // 准备邮件
        Context context = new Context();
        context.setVariable("code", code);
        context.setVariable("siteName", siteName);
        String emailContent = templateEngine.process("VerifyEmail", context);
        // 发送邮件
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject("【" + siteName + "】验证码");
        helper.setText(emailContent, true);
        mailSender.send(message);
    }
}
