package com.cyber.core.api;

import lombok.Data;
import java.util.List;

/**
 * 分页结果类
 *
 * @author cyber
 * @date 2022年5月13日
 */
@Data
public class PageResult<T> {
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 记录
     */
    private List<T> rows;

    /**
     * 每页几条
     */
    private Integer size;

    /**
     * 当前第几页
     */
    private Long currentPage;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult(Long total, List<T> rows, Integer size) {
        this.total = total;
        this.rows = rows;
        this.size = size;
    }

    public PageResult(Long total, List<T> rows, Integer size, Long currentPage) {
        this.total = total;
        this.rows = rows;
        this.size = size;
        this.currentPage = currentPage;
    }

    public PageResult() {
    }
}