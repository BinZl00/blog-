package com.binbin.weblog.web.convert;

import com.binbin.weblog.common.domain.dos.ArticleDO;
import com.binbin.weblog.web.model.vo.article.FindIndexArticlePageListRspVO;
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

}