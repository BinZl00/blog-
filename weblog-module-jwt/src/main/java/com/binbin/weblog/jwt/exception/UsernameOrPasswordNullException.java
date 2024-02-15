package com.binbin.weblog.jwt.exception;

import org.springframework.security.core.AuthenticationException;

//AuthenticationException是Spring Security中用于表示用户认证过程中发生的错误的基础异常类。
public class UsernameOrPasswordNullException extends AuthenticationException {

    public UsernameOrPasswordNullException(String msg, Throwable cause) {
        // 带原因的构造函数，子类的构造函数中通过super()来调用父类的构造函数
        super(msg, cause);
    }

    public UsernameOrPasswordNullException(String msg) {
        // 带消息的构造函数
        super(msg);
    }
}