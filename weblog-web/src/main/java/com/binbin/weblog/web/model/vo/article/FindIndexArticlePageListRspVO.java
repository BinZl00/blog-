package com.binbin.weblog.web.model.vo.article;

import com.binbin.weblog.web.model.vo.category.FindCategoryListRspVO;
import com.binbin.weblog.web.model.vo.tag.FindTagListRspVO;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "返回首页 分页 VO")
public class FindIndexArticlePageListRspVO {//主类
    private Long id;
    private String cover;
    private String title;
    private LocalDate createDate;
    private String summary;
    /**
     * 文章分类，子类
     */
    private FindCategoryListRspVO category;

    /**
     * 多个文章标签，子类
     */
    private List<FindTagListRspVO> tags;
}