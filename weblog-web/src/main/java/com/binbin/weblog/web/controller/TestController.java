package com.binbin.weblog.web.controller;

import com.binbin.weblog.common.aspect.ApiOperationLog;
import com.binbin.weblog.common.utils.JsonUtil;
import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@Slf4j
@Api(tags = "首页测试模块")
public class TestController {

    /*@PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    //BindingResult bindingResult：用于收集验证的错误信息
    public Response  test(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 检查bindingResult是否有错误。如果有错误，收集所有错误信息并返回一个ResponseEntity
        if (bindingResult.hasErrors()) {
            // 获取校验不通过字段的提示信息
            String errorMsg = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.joining(", "));

            return Response.fail(errorMsg);
        }

        // 返参
        return Response.success();
    }*/

    /*@PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 手动抛异常，入参是前面定义好的异常码枚举，返参统一交给全局异常处理器搞定
        throw new BizException(ResponseCodeEnum.PRODUCT_NOT_FOUND);
    }*/

    /*@PostMapping("/test")
    @ApiOperationLog(description = "测试接口")
    public Response test(@RequestBody @Validated User user, BindingResult bindingResult) {
        // 主动定义一个运行时异常，分母不能为零
        int i = 1 / 0;
        return Response.success();
    }*/

    @PostMapping("admin/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated User user) {
        // 打印入参  User(username=, sex=, age=, email=, createTime=, updateDate=, time=)
        log.info(JsonUtil.toJsonString(user));

        // 设置三种日期字段值
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateDate(LocalDate.now());
        user.setTime(LocalTime.now());

        return Response.success(user);
    }

    @PostMapping("admin/update")
    @ApiOperationLog(description = "测试更新接口") //自定义切面
    @ApiOperation(value = "测试更新接口")
    //基于角色的访问控制。hasRole('ROLE_ADMIN')表示只有具有ROLE_ADMIN角色的用户才能访问这个接口
    @PreAuthorize("hasRole('ROLE_ADMIN')")  //@EnableGlobalMethodSecurity方法级注解
    public Response testUpdate() {
        log.info("更新成功...");
        return Response.success();
    }

}


