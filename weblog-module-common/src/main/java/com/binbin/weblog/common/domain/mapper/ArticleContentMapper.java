package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.binbin.weblog.common.domain.dos.ArticleContentDO;

public interface ArticleContentMapper extends BaseMapper<ArticleContentDO> {
    /**
     * 根据主表文章表 t_article的 ID 对应的子表外键 articleId 删除记录
     * @param articleId
     * 使用 deleteById 方法，它只会删除 t_article_content 表中 id 字段等于传入参数的记录，这可能只删除了一条记录，
     * 而不是所有相关的文章内容记录。为实现删除所有相关记录的功能，要使用 delete 方法，并提供一个 QueryWrapper 对象来构建更复杂的查询条件。
     */
    default int deleteByArticleId(Long articleId) {//返回一个整数，被删除的记录数
        /*return delete(Wrappers.<ArticleContentDO>lambdaQuery()
                .eq(ArticleContentDO::getArticleId, articleId));*/
        LambdaQueryWrapper<ArticleContentDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ArticleContentDO::getArticleId,articleId);
        return delete(wrapper);
    }



}
