package com.binbin.weblog.jwt.filter;

import com.binbin.weblog.jwt.utils.JwtTokenHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * 后端 校验令牌 ,继承自OncePerRequestFilter，在每个请求到达受保护资源之前都会执行一次。
 * 用户信息设置到SecurityContextHolder中，这样在后续的请求处理中就可以使用这些信息
 */
@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {
//由Spring Security的配置类(如WebSecurityConfigurerAdapter)创建的，那么你就不需要在TokenAuthenticationFilter类上添加@Component注解
    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint; //Spring Security接口,未通过认证时,如何开始认证过程的行为

    @Value("${jwt.tokenPrefix}")
    private String tokenPrefix;

    @Value("${jwt.tokenHeaderKey}")
    private String tokenHeaderKey;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/admin")){//只对后台admin保护，其他url都是暴露外界的
            //前端JWT Token请求头Authorization: Bearer <token>  从请求头中获取 key 为 Authorization 的值
            String header = request.getHeader(tokenHeaderKey);
            // 判断 value 值是否以 Bearer 开头
            if (StringUtils.startsWith(header, tokenPrefix)) {
                // 截取Token令牌,由于"Bearer"有7个字符（包括空格），所以从第8个字符后开始截取
                String token = StringUtils.substring(header, 7);
                log.info("Token: {}", token);

                // 判空 Token
                if (StringUtils.isNotBlank(token)) {
                    try {
                        // 校验 Token 是否可用, 若解析异常，针对不同异常做出不同的响应参数
                        jwtTokenHelper.validateToken(token);
                    } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                        // 抛出异常，统一让 AuthenticationEntryPoint 处理响应参数
                 /*AuthenticationEntryPoint接口是Spring Security认证失败时的行为，而RestAuthenticationEntryPoint类实现了这个接口，父接口调用实现类。
                 前提是RestAuthenticationEntryPoint类已经被Spring容器管理。你提供了具体的实现类，Spring容器会使用你提供的类*/
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 不可用"));
                        return;
                    } catch (ExpiredJwtException e) {
                        authenticationEntryPoint.commence(request, response, new AuthenticationServiceException("Token 已失效"));
                        return;
                    }

                    // 从 Token 中解析出用户名
                    String username = jwtTokenHelper.getUsernameByToken(token);

                    if (StringUtils.isNotBlank(username) && Objects.isNull(SecurityContextHolder.getContext().getAuthentication())) {
                        // 根据用户名获取用户详情信息
                        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                        // 将用户信息存入 authentication 方便后续校验
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
                                userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        // 将 authentication 存入 ThreadLocal线程局部变量，方便后续获取用户信息
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }

        }

        // 继续执行写一个过滤器
        filterChain.doFilter(request, response);
    }
}
