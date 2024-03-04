package com.easyauth.security.config;

import com.easyauth.security.component.AuthAuthorizationManager;
import com.easyauth.security.component.JwtFilter;
import com.easyauth.security.component.AuthAuthenticationEntryPoint;
import com.easyauth.security.component.AuthAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;


@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Autowired
    private JwtFilter jwtFilter;

    @Autowired
    private AuthAuthenticationEntryPoint authAuthenticationEntryPoint;

    @Autowired
    private AuthAccessDeniedHandler authAccessDeniedHandler;

    @Autowired
    private AuthAuthorizationManager authAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 关闭csrf
        http.csrf(AbstractHttpConfigurer::disable);
        // 关闭表单登录
        http.formLogin(AbstractHttpConfigurer::disable);
        // 关闭httpBasic
        http.httpBasic(AbstractHttpConfigurer::disable);
        // 关闭session
        http.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 添加自定义的过滤器
        http.addFilterAfter(jwtFilter, ExceptionTranslationFilter.class); // 放在ExceptionTranslationFilter之后，确保异常交给restAuthenticationEntryPoint处理

        // 配置异常处理
        http.exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(authAuthenticationEntryPoint)
                .accessDeniedHandler(authAccessDeniedHandler));


        // 配置权限管理器
        http.authorizeHttpRequests(requests ->
                requests.requestMatchers("/auth/**").permitAll()
                        .anyRequest().access(authAuthorizationManager)
        );

        // 配置跨域,默认开启所有跨域请求
        http.cors(Customizer.withDefaults());

        return http.build();
    }

    // 配置密码加密算法
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
