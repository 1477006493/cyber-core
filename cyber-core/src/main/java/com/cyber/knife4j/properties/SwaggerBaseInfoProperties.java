package com.cyber.knife4j.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


/**
 * 配置Swagger基础描述信息
 *
 * @author cyber
 * @date 2022年7月7日
 */
@Configuration
@ConfigurationProperties(prefix = "cyber.swagger.base")
@Data
public class SwaggerBaseInfoProperties {
    /**
     * Swagger标题
     */
    private String title = "cyber的博客在线API接口文档";

    /**
     * 作者信息
     */
    private String author = "cyber";

    /**
     * 描述信息
     */
    private String description = "cyber的博客在线API接口文档";

    /**
     * 作者URL主页
     */
    private String url = "https://palewl.cn";

    /**
     * 邮箱
     */
    private String email = "1477006493@qq.com";

    /**
     * 版本号
     */
    private String version = "v1.0.1";

    /**
     * 是否开启Swagger
     */
    private Boolean enabled = true;

}
