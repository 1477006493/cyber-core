package com.cyber.email.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cyber
 * @date 2022年8月15日
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "cyber.email")
public class EmailProperties {

    /**
     * 协议
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 服务器是否要身份认证
     */
    private Boolean auth = true;

    /**
     * 超时时间
     */
    private Integer timeout = 25000;

}
