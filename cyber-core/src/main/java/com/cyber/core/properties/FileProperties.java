package com.cyber.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author cyber
 * @Description 本地文件路径配置属性类
 * @date 2022/5/17
 * @Version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "cyber.file")
public class FileProperties {

    /**
     * 文件上传到本地服务器的地址
     */
    private String localFilePath;

    /**
     * 下载的文存储到本地服务器的地址
     */
    private String downloadFilePath;

    /**
     * 访问的基础url
     */
    private String baseUrl;
}
