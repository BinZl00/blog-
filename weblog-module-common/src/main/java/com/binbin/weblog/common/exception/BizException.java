package com.binbin.weblog.common.exception;

import com.binbin.weblog.common.exception.BaseExceptionInterface;
import lombok.Getter;
import lombok.Setter;

/**
 *  用于自定义业务异常类
 **/
@Getter
@Setter
public class BizException extends RuntimeException {
    // 异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

    //一个有参构造器，入参是自定义 BaseExceptionInterface，可以方便的构造一个业务异常。
    public BizException(BaseExceptionInterface baseExceptionInterface) {
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errorMessage = baseExceptionInterface.getErrorMessage();
    }
}
