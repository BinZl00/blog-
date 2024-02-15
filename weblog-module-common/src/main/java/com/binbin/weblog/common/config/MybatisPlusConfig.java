package com.binbin.weblog.common.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 *  Mybatis Plus 配置文件
 **/
@Configuration
@MapperScan("com.binbin.weblog.common.domain.mapper")
public class MybatisPlusConfig {
}