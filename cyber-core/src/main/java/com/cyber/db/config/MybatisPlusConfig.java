package com.cyber.db.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @author cyber
 * @Description mybatisPlus配置类相关
 * @date 2022/5/13
 * @Version 1.0
 */
@Configuration
public class MybatisPlusConfig implements MetaObjectHandler {

    /**
     * 配置分页插件
     *
     * @return MybatisPlusInterceptor
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        return interceptor;
    }

    /**
     * 配置插入时自动填充
     *
     * @param metaObject metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        //设置插入时自动注入时间
        this.setFieldValByName("createTime", new Date(), metaObject);
        this.setFieldValByName("updateTime", new Date(), metaObject);
        this.setFieldValByName("status", 1, metaObject);
        this.setFieldValByName("isDeleted", 0, metaObject);
    }

    /**
     * 配置插入时自动填充
     *
     * @param metaObject metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        //设置更新时自动注入时间
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
