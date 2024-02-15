package com.binbin.weblog.jwt.handler;

import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.jwt.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 处理当用户登录成功时，访问受保护的资源，但是权限角色控制。
 * AccessDeniedHandler是Spring Security框架中的接口，它负责处理已认证用户访问受保护资源时由于权限不足而导致的拒绝访问异常（AccessDeniedException）
 */
@Slf4j
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.warn("登录成功访问收保护的资源，但是权限不够: ", accessDeniedException);
        // 预留，后面引入多角色时会用到
        ResultUtil.fail(response, Response.fail(ResponseCodeEnum.FORBIDDEN));
    }
}