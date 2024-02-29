package com.easyauth.security.config;

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


@EnableWebSecurity
@Configuration
public class SecurityConfig {
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

        //TODO 实现自定义filter

        // 配置放行规则
//        http.authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests
//                        .requestMatchers("/public/**").permitAll()
//                        .requestMatchers("/hello/**").permitAll()
//                        .requestMatchers("/auth/**").permitAll()
//                        .anyRequest().authenticated()
//        );
        http.authorizeHttpRequests(requests -> requests.anyRequest().permitAll());
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
