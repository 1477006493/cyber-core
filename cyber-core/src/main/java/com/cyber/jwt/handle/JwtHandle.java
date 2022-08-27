package com.cyber.jwt.handle;

import com.cyber.jwt.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JWT令牌实现
 *
 * @author cyber
 * @date 2022年7月7日
 */
@Component
@EnableConfigurationProperties(JwtProperties.class)
public class JwtHandle {

    @Autowired
    private JwtProperties jwtProperties;

    public static final String ROLES = "roles";

    /**
     * 生成JWT
     *
     * @param userId   主键
     * @param userinfo 用户信息
     * @param roles    角色
     * @return TokenVO
     */
    public TokenInfo createJwt(Long userId, String userinfo, String roles) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userId.toString())
                .setSubject(userinfo)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getKey()).claim(ROLES, roles);
        if (jwtProperties.getTtl() > 0) {
            builder.setExpiration(new Date(nowMillis + jwtProperties.getTtl()));
        }
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setAccessToken(builder.compact());
        tokenInfo.setExpire(this.jwtProperties.getTtl());
        tokenInfo.setUserId(userId);
        return tokenInfo;
    }

    /**
     * 解析JWT
     *
     * @param jwtStr token
     * @return 解析出来的jwt对象
     */
    public Claims parseJwt(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(jwtProperties.getKey())
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}
