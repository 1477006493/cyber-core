package com.cyber.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 配置放行地址
 *
 * @author cyber
 * @date 2022年7月12日
 */
@Data
@Component
@ConfigurationProperties(prefix = "cyber.secure")
public class SecureProperties {
    /**
     * 需要跳过拦截的url
     */
    private final List<String> skip = new ArrayList<>();
}
