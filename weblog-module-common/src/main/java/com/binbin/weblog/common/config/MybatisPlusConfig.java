package com.binbin.weblog.common.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *  Mybatis Plus 配置文件
 **/
@Configuration
@MapperScan("com.binbin.weblog.common.domain.mapper")
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
   //aginationInnerInterceptor是MyBatis Plus提供的一个分页拦截器，它会在执行查询操作时自动处理分页逻辑
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }

}