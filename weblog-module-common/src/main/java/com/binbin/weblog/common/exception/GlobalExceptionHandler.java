package com.binbin.weblog.common.exception;

import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


/**
    全局异常处理响应类，其内的方法可以捕获控制器层抛出的所有异常
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**通过 @ControllerAdvice注解，将 GlobalExceptionHandler 声明为了全局异常处理类
     * 捕获自定义业务异常
     */
    @ExceptionHandler({ BizException.class }) //@ExceptionHandler 注解指定只捕获 BizException 自定义业务异常
    @ResponseBody
    public Response<Object> handleBizException(HttpServletRequest request, BizException e) {
        log.warn("{} request fail, errorCode: {}, errorMessage: {}", request.getRequestURI(), e.getErrorCode(), e.getErrorMessage());
        return Response.fail(e);
    }


    /**
     * 其他类型异常，默认将它们当作系统内部错误处理
     */
    @ExceptionHandler({ Exception.class })
    @ResponseBody
    public Response<Object> handleOtherException(HttpServletRequest request, Exception e) {
        log.error("{} request error, ", request.getRequestURI(), e);
        return Response.fail(ResponseCodeEnum.SYSTEM_ERROR);
    }


    /**
     * 捕获 参数校验 @Valid的异常，验证失败时，Spring会抛出MethodArgumentNotValidException
     * 这个异常包含了失败验证的 BindingResult 对象，这个对象来获取关于验证失败的详细信息
     * @return
     */
    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseBody
    public Response<Object> handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        // 参数错误异常码
        String errorCode = ResponseCodeEnum.PARAM_NOT_VALID.getErrorCode();

        // 获取 BindingResult
        BindingResult bindingResult = e.getBindingResult();

        StringBuilder sb = new StringBuilder(); //拼接字符串

        /* 获取校验不通过的字段，并组合错误信息，格式为： email 邮箱格式不正确, 当前值: '123124qq.com';
        Optional类是用于表示一个值可能存在也可能不存在的情况。*/
        Optional.ofNullable(bindingResult.getFieldErrors()).ifPresent( errors -> {
            errors.forEach(error ->
                    sb.append(error.getField())
                            .append(" ")
                            .append("系统验证:").append(error.getDefaultMessage())
                            .append(", 当前值: ' ")
                            .append(error.getRejectedValue())
                            .append(" ' ; ")

            );
        } );

        // 拼接后的错误信息
        String errorMessage = sb.toString();

        log.warn("{} request error, errorCode: {}, errorMessage: {}", request.getRequestURI(), errorCode, errorMessage);

        return Response.fail(errorCode, errorMessage);

    }

    /**
     * AccessDeniedException异常通常在用户尝试访问他们没有权限的资源时抛出
     * @param e
     * @throws AccessDeniedException
     */
    @ExceptionHandler({ AccessDeniedException.class })
    public void throwAccessDeniedException(AccessDeniedException e) throws AccessDeniedException {
        // 捕获到鉴权失败异常，主动抛出，交给 RestAccessDeniedHandler 去处理
        log.info("============= 捕获到 AccessDeniedException");
        throw e;
    }

}
