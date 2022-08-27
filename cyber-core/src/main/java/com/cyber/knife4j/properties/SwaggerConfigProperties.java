package com.cyber.knife4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


/**
 * 配置Swagger各个模块
 *
 * @author cyber
 * @date 2022年7月7日
 */
@Configuration
@ConfigurationProperties(prefix = "cyber.swagger")
@Data
public class SwaggerConfigProperties {
    /**
     * 配置Swagger路由信息
     */
    private  List<Router> routers = new ArrayList<>();
}
