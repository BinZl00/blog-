package com.binbin.weblog.admin.model.vo.article;

import com.binbin.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "查询文章分页数据入参 VO")
public class FindArticlePageListReqVO extends BasePageQuery {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 发布的起始日期
     */
    private LocalDate startDate;

    /**
     * 发布的结束日期
     */
    private LocalDate endDate;

}
/**
 {
     "current": 1, // 要查询的页码
     "size": 10, // 每页要展示的数据量
     "searchTitle": "", // 查询的文章标题（支持模糊查询）
     "startDate": "", // 起始创建时间
     "endDate": "", // 结束创建时间
 }
 */