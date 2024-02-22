package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.article.DeleteArticleReqVO;
import com.binbin.weblog.admin.model.vo.article.FindArticleDetailReqVO;
import com.binbin.weblog.admin.model.vo.article.FindArticlePageListReqVO;
import com.binbin.weblog.admin.model.vo.article.PublishArticleReqVO;
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

}
