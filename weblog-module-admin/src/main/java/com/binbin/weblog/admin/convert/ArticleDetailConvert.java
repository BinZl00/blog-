package com.binbin.weblog.admin.convert;

import com.binbin.weblog.admin.model.vo.article.FindArticleDetailRspVO;
import com.binbin.weblog.common.domain.dos.ArticleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ArticleDetailConvert {//数据库 DO 类转换为 VO 视图类

    /**
     * 初始化 convert 实例
     */
    ArticleDetailConvert INSTANCE = Mappers.getMapper(ArticleDetailConvert.class);

    /**
     * 将 DO 转化为 VO
     */
    FindArticleDetailRspVO convertDO2VO(ArticleDO bean);

}
