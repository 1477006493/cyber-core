package com.cyber.db.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.Executor;

@Slf4j
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {

    @Autowired
    private Executor asyncExecutor;

    @Override
    public void asyncSave(T t) {
        asyncExecutor.execute(() -> {
            save(t);
        });
    }

    @Override
    public <E> Page<E> getPage(Integer page, Integer size) {
        return WrapperUtil.getPage(page,size);
    }

    @Override
    public <E> Page<E> getPage() {
       return WrapperUtil.getPage(1,15);
    }

    @Override
    public <E> QueryWrapper<E> getWrapper(E e) {
        return WrapperUtil.getWrapper(e);
    }
}
