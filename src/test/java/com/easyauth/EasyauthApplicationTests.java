package com.easyauth;

import com.easyauth.domain.DTO.EmployeePageQueryDTO;
import com.easyauth.domain.entity.Employee;
import com.easyauth.mapper.EmployeeMapper;
import com.easyauth.service.RedisService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class EasyauthApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    void contextLoads() {
        Employee employee = (Employee) redisService.get("employee:admin");
        System.out.println(employee);
    }
}
