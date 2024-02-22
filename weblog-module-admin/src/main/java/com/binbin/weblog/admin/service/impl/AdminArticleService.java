package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.binbin.weblog.common.utils.Response;

public interface AdminArticleService {
    /**
     * 发布文章
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

}
