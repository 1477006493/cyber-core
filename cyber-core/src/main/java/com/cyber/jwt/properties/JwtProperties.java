package com.cyber.jwt.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author smy
 * @Description JwtProperties
 * @date 2022/5/18 9:14
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "cyber.jwt.config")
public class JwtProperties {
    /**
     * 密钥
     */
    private String key = "c24f7e0cdcda86b547557e39ccbcba4d";

    /**
     * 过期时间
     */
    private Long ttl = 3600000000L;
}
