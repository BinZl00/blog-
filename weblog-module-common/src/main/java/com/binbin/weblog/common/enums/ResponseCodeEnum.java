package com.binbin.weblog.common.enums;

import com.binbin.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *响应异常码枚举类,实现 BaseExceptionInterface 自定义异常接口
 **/
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    // ----枚举常量  通用异常状态码  @Getter生成两个errorCode和errorMessage的getter方法-----------
    SYSTEM_ERROR("10000", "出错啦，后台小哥正在努力修复中..."),

    //参数验证
    PARAM_NOT_VALID("10001", "参数错误"),

    // ----------- 业务异常状态码 -----------
    PRODUCT_NOT_FOUND("20000", "该产品不存在（测试使用）"),

    //Spring Security 用户认证失败的响应码
    LOGIN_FAIL("20000", "登录失败"),
    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    //未登录
    UNAUTHORIZED("20002", "无访问权限，请先登录！"),

    FORBIDDEN("20004", "演示账号仅支持查询操作！"),
    ;   //枚举类型的结束，用分号;


    // 成员变量，异常码
    private String errorCode;
    // 错误信息
    private String errorMessage;

}