package com.binbin.weblog.common.exception;

/**
 * 通用异常接口，两个方法，getErrorCode()获取异常码，getErrorMessage()获取异常信息
 **/
public interface BaseExceptionInterface {
    String getErrorCode();

    String getErrorMessage();
}
