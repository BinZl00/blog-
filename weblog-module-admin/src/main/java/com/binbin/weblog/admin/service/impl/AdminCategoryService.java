package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.FindCategoryPageListReqVO;
import com.binbin.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.binbin.weblog.common.utils.PageResponse;
import com.binbin.weblog.common.utils.Response;

public interface AdminCategoryService {
    /**
     * 添加分类
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

    /**
     * 分类分页数据查询
     */
    PageResponse findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO);

}