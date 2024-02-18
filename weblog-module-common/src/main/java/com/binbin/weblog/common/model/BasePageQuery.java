package com.binbin.weblog.common.model;

import lombok.Data;

/**
 * 分页请求中，通常入参中都会带有请求页码、每页要展示的数据量
 */
@Data
public class BasePageQuery {
    /**
     * 当前页码, 默认第一页
     */
    private Long current = 1L;
    /**
     * 每页展示的数据数量，默认每页展示 10 条数据
     */
    private Long size = 10L;
}