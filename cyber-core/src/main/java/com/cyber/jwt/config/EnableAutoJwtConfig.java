package com.cyber.jwt.config;

import com.cyber.jwt.handle.JwtHandle;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * spring自动装配
 *
 * @author cyber
 * @date 2022年6月24日
 */
@Configuration(
        proxyBeanMethods = false
)
@Import(JwtHandle.class)
@ConditionalOnWebApplication
public class EnableAutoJwtConfig {
    public EnableAutoJwtConfig() {
    }
}
