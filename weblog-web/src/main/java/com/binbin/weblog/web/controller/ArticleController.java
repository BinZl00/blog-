package com.binbin.weblog.web.controller;

import com.binbin.weblog.common.aspect.ApiOperationLog;
import com.binbin.weblog.common.utils.Response;
import com.binbin.weblog.web.model.vo.article.FindArticleDetailReqVO;
import com.binbin.weblog.web.model.vo.article.FindIndexArticlePageListReqVO;
import com.binbin.weblog.web.service.impl.ArticleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
@Api(tags = "文章")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping("/list")
    @ApiOperation(value = "获取首页文章分页数据")
    @ApiOperationLog(description = "获取首页文章分页数据")
    public Response findArticlePageList(@RequestBody FindIndexArticlePageListReqVO findIndexArticlePageListReqVO) {
        return articleService.findArticlePageList(findIndexArticlePageListReqVO);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "打开文章详情")
    @ApiOperationLog(description = "打开文章详情")
    public Response findArticleDetail(@RequestBody FindArticleDetailReqVO findArticleDetailReqVO) {
        return articleService.findArticleDetail(findArticleDetailReqVO);
    }

}
