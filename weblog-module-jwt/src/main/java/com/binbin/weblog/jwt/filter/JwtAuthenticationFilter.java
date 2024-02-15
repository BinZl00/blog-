package com.binbin.weblog.jwt.filter;

import com.binbin.weblog.jwt.exception.UsernameOrPasswordNullException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 前端和后端分离。需要自定义认证的过滤器 处理前端发送的JWT Token。
 * 继承AbstractAuthenticationProcessingFilter，处理 JWT（JSON Web Token）的用户身份验证过程
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 调用父类AbstractAuthenticationProcessingFilter的构造函数，指定了处理用户登录的访问地址。
     * 前端请求路径 /login ，请求方法为 POST 时，该过滤器将被触发
     */
    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();
        // 解析前端提交的 JSON 数据
        JsonNode jsonNode = mapper.readTree(request.getInputStream());
        JsonNode usernameNode = jsonNode.get("username");
        JsonNode passwordNode =  jsonNode.get("password");

        // 判断用户名、密码是否为空
        if (Objects.isNull(usernameNode) || Objects.isNull(passwordNode)
             ||  StringUtils.isBlank(usernameNode.textValue()) || StringUtils.isBlank(passwordNode.textValue())) {
            throw new UsernameOrPasswordNullException("用户名或密码不能为空");
        }

        String username = usernameNode.textValue();
        String password = passwordNode.textValue();

        // 将用户名、密码封装到 认证令牌 中，用于在Spring Security的认证流程中进行用户身份验证
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(username, password);
        //返回是一个被填充了用户详细信息及权限信息的已认证Authentication对象
        return getAuthenticationManager().authenticate(usernamePasswordAuthenticationToken);

        /*接下来 AuthenticationManager在认证过程中调用UserDetailsService实现类UserDetailServiceImpl.loadUserByUsername方法来查找用户信息。
        这个方法负责根据提供的用户名从数据源（如数据库）中检索用户详细信息，生成的UserDetails对象被返回给AuthenticationManager*/


    }
}
