package com.binbin.weblog.web.convert;

import com.binbin.weblog.common.domain.dos.ArticleDO;
import com.binbin.weblog.web.model.vo.archive.FindArchiveArticleRspVO;
import com.binbin.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
import com.binbin.weblog.web.model.vo.category.FindCategoryArticlePageListRspVO;
import com.binbin.weblog.web.model.vo.tag.FindTagArticlePageListRspVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleConvert {
    /**
     * 静态实例，加载到JVM虚拟机一直存在，在MapStruct中，Mappers.getMapper(ArticleConvert.class) 会返回一个 ArticleConvert 类型的实例
     * 不需要每次在其他地方注入，new实例
     */
    ArticleConvert INSTANCE = Mappers.getMapper(ArticleConvert.class);

    /**
     * 将 DO 转化为 VO
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindIndexArticlePageListRspVO convertDO2VO(ArticleDO bean);

    /**
     * 将 DO 转化为归档文章 VO
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    @Mapping(target = "createMonth", expression = "java(java.time.YearMonth.from(bean.getCreateTime()))")
    FindArchiveArticleRspVO convertDO2ArchiveArticleVO(ArticleDO bean);

    /**
     * 将 DO 转换成分类文章 VO
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindCategoryArticlePageListRspVO convertDO2CategoryArticleVO(ArticleDO bean);

    /**
     * ArticleDO -> FindTagArticlePageListRspVO
     */
    @Mapping(target = "createDate", expression = "java(java.time.LocalDate.from(bean.getCreateTime()))")
    FindTagArticlePageListRspVO convertDO2TagArticleVO(ArticleDO bean);

}