package com.binbin.weblog.web.model.vo.article;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder //上下篇文章返回 VO
public class FindPreNextArticleRspVO {
    /**
     * 文章 ID
     */
    private Long articleId;

    /**
     * 文章标题
     */
    private String articleTitle;
}