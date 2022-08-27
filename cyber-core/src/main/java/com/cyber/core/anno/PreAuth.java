package com.cyber.core.anno;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 自定义注解，作用于方法上，用于权限校验
 * @author cyber
 * @date 2022年7月12日
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface PreAuth {
    /**
     * value 支持按角色划分权限
     * @return value
     */
    String value();
}
