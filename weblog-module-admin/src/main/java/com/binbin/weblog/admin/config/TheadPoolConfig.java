package com.binbin.weblog.admin.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
/* 启用 Spring 的异步，要在一个方法上执行异步，也就是该方法在 后台线程 而不是主线程中 运行。
Spring才能处理标记@Async的方法，调用@Async注解的方法时，不是在主线程同步执行，而是将任务提交到线程池中 */
public class TheadPoolConfig {//一个进程可以包含多个线程

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 常驻线程数量
        executor.setMaxPoolSize(20); // 最大线程数
        executor.setQueueCapacity(100); // 等待队列容量
        executor.setThreadNamePrefix("WeblogThreadPool-"); // 线程名前缀
        executor.initialize();
        return executor;
    }

}