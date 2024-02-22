package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.binbin.weblog.common.config.InsertBatchMapper;
import com.binbin.weblog.common.domain.dos.ArticleTagRelDO;

public interface ArticleTagRelMapper extends InsertBatchMapper<ArticleTagRelDO> {
    /**
     * 根据主表文章表 t_article的 ID 对应的子表外键 articleId 删除记录 删除关联记录
     * @param articleId
     */
    default int deleteByArticleId(Long articleId) {
        return delete(Wrappers.<ArticleTagRelDO>lambdaQuery()
                .eq(ArticleTagRelDO::getArticleId, articleId));
    }

}
