package com.binbin.weblog.jwt.config;

import com.binbin.weblog.jwt.filter.JwtAuthenticationFilter;
import com.binbin.weblog.jwt.handler.RestAuthenticationFailureHandler;
import com.binbin.weblog.jwt.handler.RestAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * JWT认证流程集成到Spring Security的用户认证中，将之前写好过滤器、认证成功、失败处理器，以及加密算法整合到了 httpSecurity
 */
@Configuration
public class JwtAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired //有@Component可以注入
    private RestAuthenticationSuccessHandler restAuthenticationSuccessHandler;

    @Autowired
    private RestAuthenticationFailureHandler restAuthenticationFailureHandler;

    @Autowired
    private PasswordEncoder passwordEncoder; //Spring Security源码

    @Autowired
    private UserDetailsService userDetailsService; //Spring Security源码

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        // 首先会通过JwtAuthenticationFilter进行JWT认证
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter();
        filter.setAuthenticationManager(httpSecurity.getSharedObject(AuthenticationManager.class));

        // 设置登录认证对应的处理类（成功处理、失败处理）
        filter.setAuthenticationSuccessHandler(restAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(restAuthenticationFailureHandler);

        // 直接使用 DaoAuthenticationProvider, 它是Spring Security 提供的默认的身份验证提供者之一
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // 设置 userDetailService，用于获取用户的详细信息
        provider.setUserDetailsService(userDetailsService);
        // 设置加密算法
        provider.setPasswordEncoder(passwordEncoder);
        httpSecurity.authenticationProvider(provider);
     /*JwtAuthenticationFilter被创建并设置为在UsernamePasswordAuthenticationFilter之前执行。
       这意味着在传统的基于表单的用户名/密码认证之前，首先会尝试通过JWT进行用户认证。*/
        httpSecurity.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
