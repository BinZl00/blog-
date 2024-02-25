package com.binbin.weblog.web.model.vo.category;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "返回首页 分类 VO")
public class FindCategoryListRspVO {
    private Long id;
    private String name;
}
