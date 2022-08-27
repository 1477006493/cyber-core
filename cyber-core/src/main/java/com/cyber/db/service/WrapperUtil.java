package com.cyber.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 条件构造
 */
public class WrapperUtil {

    public static <T> Page<T> getPage(Integer page,Integer size) {
        if (page == null || page == 0){
            page = 1;
        }
        return new Page<T>(page,size);
    }

    public static <T> QueryWrapper<T> getWrapper(T t){
        return new QueryWrapper<>(t);
    }
}
