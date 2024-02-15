package com.binbin.weblog.admin.config;

import com.binbin.weblog.jwt.config.JwtAuthenticationSecurityConfig;
import com.binbin.weblog.jwt.filter.RestAuthenticationEntryPoint;
import com.binbin.weblog.jwt.filter.TokenAuthenticationFilter;
import com.binbin.weblog.jwt.handler.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//Spring Security总配置，@EnableWebSecurity对Web应用程序启用基于URL的安全性，对HTTP请求的拦截、身份验证（如登录、注销）、授权（如基于角色的访问控制）
@EnableWebSecurity
//@EnableGlobalMethodSecurity时，应用程序启用方法级别的安全性。使用注解（如 @PreAuthorize、@PostAuthorize、@Secured 等）来保护方法，而不是仅仅保护URL。
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationSecurityConfig jwtAuthenticationSecurityConfig;
    @Autowired
    private RestAuthenticationEntryPoint authEntryPoint; //未登录
    @Autowired
    private RestAccessDeniedHandler deniedHandler; //登录成功，角色受限

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(). // 前后端分离，不需要 csrf
                formLogin().disable() // 禁用表单登录，用JWT认证，传统的表单登录（如使用用户名和密码登录表单）不再适用
                .apply(jwtAuthenticationSecurityConfig) // 设置用户登录认证相关配置
                .and()
                .authorizeHttpRequests()
                .mvcMatchers("/admin/**").authenticated() // 认证所有以 /admin 为前缀的 URL 资源
                .anyRequest().permitAll() // 其他都需要放行，无需认证
                .and()
                .httpBasic().authenticationEntryPoint(authEntryPoint) // 处理用户未登录访问受保护的资源的情况
                .and()
                .exceptionHandling().accessDeniedHandler(deniedHandler) // 处理登录成功后访问受保护的资源，但是权限不够的情况
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 前后端分离，无需创建会话
                .and()
           //将Token校验令牌过滤器添加到默认过滤器之前，UsernamePasswordAuthenticationFilter.class是默认的用户密码表单认证
                .addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    /**
     * Token 校验过滤器
     * @return
     */
    @Bean
    public TokenAuthenticationFilter tokenAuthenticationFilter() {
     //在TokenAuthenticationFilter类上添加@Component注解，Spring容器报错提示你存在重复的bean定义
        return new TokenAuthenticationFilter();
    }

}
