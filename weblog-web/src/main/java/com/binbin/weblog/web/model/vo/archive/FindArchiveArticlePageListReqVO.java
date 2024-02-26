package com.binbin.weblog.web.model.vo.archive;

import com.binbin.weblog.common.model.BasePageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "文章归档分页入参 VO")
public class FindArchiveArticlePageListReqVO extends BasePageQuery {
}