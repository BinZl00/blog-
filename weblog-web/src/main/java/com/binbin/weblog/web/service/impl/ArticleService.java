package com.binbin.weblog.web.service.impl;

import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
    /**
     * 获取首页文章分页数据
     */
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);



}