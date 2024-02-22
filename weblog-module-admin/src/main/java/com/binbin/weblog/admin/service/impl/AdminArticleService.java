package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.article.*;
import com.binbin.weblog.common.utils.Response;

public interface AdminArticleService {
    /**
     * 发布文章
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);

    /**
     * 查询文章分页数据
     */
    Response findArticlePageList(FindArticlePageListReqVO findArticlePageListReqVO);

    /**
     * 查询文章详情
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

    /**
     * 更新文章
     */
    Response updateArticle(UpdateArticleReqVO updateArticleReqVO);
}
