package com.binbin.weblog.admin.model.vo.category;

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
@ApiModel(value = "查询分类分页数据入参 VO")
public class FindCategoryPageListReqVO extends BasePageQuery {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建的起始日期
     */
    private LocalDate startDate;

    /**
     * 创建的结束日期
     */
    private LocalDate endDate;
}
/**
 * { 前端入参
 *   "current": 1, // 要查询的页码
 *   "size": 10, // 每页要展示的数据量
 *   -----以上在BasePageQuery通用类----------
 *   "name": "", // 分类名称
 *   "startDate": "", // 起始创建时间
 *   "endDate": "", // 结束创建时间
 * }
 */