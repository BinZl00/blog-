package com.binbin.weblog.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *  用户密码加密测试生成
 */
@Component
public class PasswordEncoderConfig {

    @Bean//PasswordEncoder 接口是 Spring Security提供的密码加密接口
    public PasswordEncoder passwordEncoder() {
        // BCrypt 是密码存储的哈希算法，它在进行哈希时会自动加入“盐”，增加密码的安全性。
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//Spring Security提供的密码加密器
        System.out.println(encoder.encode(" ")); //显示密码"XX"加密后
    }

}
