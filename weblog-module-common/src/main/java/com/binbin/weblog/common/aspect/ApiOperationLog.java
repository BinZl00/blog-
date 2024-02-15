package com.binbin.weblog.common.aspect;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {
    /**
     * 切面  切点注解接口，记录控制器操作日志
     *
     */
    String description() default "";

}
