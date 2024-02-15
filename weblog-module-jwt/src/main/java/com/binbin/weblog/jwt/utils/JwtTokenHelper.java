package com.binbin.weblog.jwt.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;

/**
 * jwt就是服务端密钥验证客户端，不用每次都查询数据库验证用户凭据，而实现无状态认证。
 * 实现InitializingBean接口, Spring容器在依赖注入后 自动调用 JwtTokenHelper工具类里的方法
 */
@Component //Spring容器管理，在需要的时候创建它的实例Bean，或其他地方需要注入
public class JwtTokenHelper implements InitializingBean {

    /**
     * 签发人，Spring会从配置文件（yml）中查找jwt.issuer的属性
     */
    @Value("${jwt.issuer}")
    private String issuer;
    /**
     * 秘钥
     */
    private Key key;

    /**
     * JWT 解析
     */
    private JwtParser jwtParser;

    /**
     * Token 失效时间（分钟）
     */
    @Value("${jwt.tokenExpireTime}")
    private Long tokenExpireTime;

    /**
     * Bean的属性setter方法，配置文件中的 Base 64 编码 key 为秘钥
     * @param base64Key
     */
    @Value("${jwt.secret}")
    public void setBase64Key(String base64Key) {
        // Keys.hmacShaKeyFor(将Base64编码的字符串解码为字节数组)
        key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(base64Key));
    }

    /**
     * 生成 Token，带一个包含用户名、签发人、签发时间、过期时间等信息的JWT令牌。
     * @param username
     * @return
     */
    public String generateToken(String username) {
        LocalDateTime now = LocalDateTime.now();//获取当前时间
        LocalDateTime expireTime = now.plusMinutes(tokenExpireTime); //设置过期时间

        return Jwts.builder().setSubject(username)
                .setSubject(username)
                .setIssuer(issuer)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key)
                .compact(); //将构建好的JWT转换为字符串，这个字符串就是最终的Token
    }


    /**
     * 服务器端初始化 JwtParser对象用于解析JWT令牌，服务器会使用这个解析器来验证Token的有效性，包括检查签名、签发人、过期时间等。
     * 不同服务器之间可能存在时钟偏移，setAllowedClockSkewSeconds()用于设置能够容忍的最大的时钟误差
     * 比当前服务器时间晚10秒，JWT仍然被认为是有效的
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        jwtParser = Jwts.parserBuilder().requireIssuer(issuer)
                .setSigningKey(key).setAllowedClockSkewSeconds(10)
                .build();
    }

    /**用户登录后，服务器生成一个JWT并发送给客户端。客户端在后续请求中将这个Token作为身份验证的凭证。
     * 解析 客户端的Token，验证令牌的合法性
     * @param token
     * @return
     */
    public Jws<Claims> parseToken(String token) {
        try {
            return jwtParser.parseClaimsJws(token);
        } catch (SignatureException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            throw new BadCredentialsException("Token 不可用", e);
        } catch (ExpiredJwtException e) {
            throw new CredentialsExpiredException("Token 失效", e);
        }
    }

    /**
     * 校验 Token 是否可用
     * @param token
     * @return
     */
    public void validateToken(String token) {
        jwtParser.parseClaimsJws(token);
    }

    /**
     * 解析 Token 获取用户名
     * @param token
     * @return
     */
    public String getUsernameByToken(String token) {
        try {
            Claims claims = jwtParser.parseClaimsJws(token).getBody();
            String username = claims.getSubject();
            return username;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 生成一个 Base64 秘钥，测试
     * @return
     */
    private static String generateBase64Key() {
        // 生成安全秘钥
        Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        // 将密钥进行 Base64 编码
        String base64Key = Base64.getEncoder().encodeToString(secretKey.getEncoded());
        return base64Key;
    }

    public static void main(String[] args) {//生成Base 64 编码的token测试
        String key = generateBase64Key();
        System.out.println("key: " + key);
    }
}

