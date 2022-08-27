package com.cyber.knife4j.properties;

import lombok.Data;

@Data
public  class Router {
    /**
     * 路由名称
     */
    private String name;

    /**
     * 路由包扫描路径
     */
    private String uri;

    /**
     * 包扫描路径
     */
    private String location;
}
