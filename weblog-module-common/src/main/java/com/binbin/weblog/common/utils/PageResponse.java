package com.binbin.weblog.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PageResponse<T> extends Response<List<T>> {

    /**
     * 总记录数
     */
    private long total = 0L;

    /**
     * 每页显示的记录数，默认每页显示 10 条
     */
    private long size = 10L;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 成功分页响应，
     * IPage page入参，由 MyBatis Plus提供，包含了分页查询的相关信息。
     */
    public static <T> PageResponse<T> success(IPage page, List<T> data) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setCurrent(Objects.isNull(page) ? 1L : page.getCurrent());
        response.setSize(Objects.isNull(page) ? 10L : page.getSize());
        response.setPages(Objects.isNull(page) ? 0L : page.getPages());
        response.setTotal(Objects.isNull(page) ? 0L : page.getTotal());
        response.setData(data);
        return response;
    }

}
/**
 { 后端返参
 "success": true,
 "message": null,
 "errorCode": null,
 "data": [
     {
     "name": "测试分类",
     "createTime": "2023-09-18 12:02:31"
     },

 ],
 "total": 4, // 总记录数
 "size": 10, // 每页展示的记录数
 "current": 1, // 当前页码
 "pages": 1 // 总共多少页
 }
 */