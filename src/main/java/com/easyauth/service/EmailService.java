package com.easyauth.service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendVerifyCode(String to) throws MessagingException;
}
