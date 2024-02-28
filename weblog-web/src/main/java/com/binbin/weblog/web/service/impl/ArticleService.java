package com.binbin.weblog.web.service.impl;

import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;
import com.binbin.weblog.web.model.vo.article.FindArticleDetailReqVO;

public interface ArticleService {
    /**
     * 获取首页文章分页数据
     */
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);

    /**
     * 打开文章详情
     * @param findArticleDetailReqVO
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);

}