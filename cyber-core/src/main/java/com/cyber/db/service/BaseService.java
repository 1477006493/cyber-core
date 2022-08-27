package com.cyber.db.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Collection;

/**
 * 拓展mybatis-plus的Service，增加异步操作
 *
 * @param <T>
 */
public interface BaseService<T> extends IService<T> {

    /**
     * 异步插入
     *
     * @param t t
     */
    void asyncSave(T t);


    <E> Page<E> getPage(Integer page, Integer size);

    <E> Page<E> getPage();

    <E> QueryWrapper<E> getWrapper(E e);


}
