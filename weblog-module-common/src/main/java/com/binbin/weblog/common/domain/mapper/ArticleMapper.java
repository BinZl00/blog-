package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.binbin.weblog.common.domain.dos.ArticleDO;

import java.time.LocalDate;
import java.util.Objects;

public interface ArticleMapper extends BaseMapper<ArticleDO> {
    /**
     * 分页查询
     * @param current 当前页码
     * @param size 每页展示的数据量
     * @param title 文章标题，模糊查询
     * @param startDate 开始时间
     * @param endDate 结束时间

     */
    default Page<ArticleDO> selectPageList(Long current, Long size, String title, LocalDate startDate, LocalDate endDate) {
        // 分页对象(查询第几页、每页多少数据)，分页后的文章列表
        Page<ArticleDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<ArticleDO> wrapper = Wrappers.<ArticleDO>lambdaQuery()
            // .条件(判空布尔值，数据库字段，入参)
                .like(StringUtils.isNotBlank(title), ArticleDO::getTitle, title.trim()) // like 模块查询
                .ge(Objects.nonNull(startDate), ArticleDO::getCreateTime, startDate) // 大于等于 startDate
                .le(Objects.nonNull(endDate), ArticleDO::getCreateTime, endDate)  // 小于等于 endDate
                .orderByDesc(ArticleDO::getCreateTime); // 按创建时间倒叙
//返回包含查询结果的 Page 对象，包含了分页信息（如当前页码、每页大小、总记录数等）以及 前端搜索框的查询结果列表。
        return selectPage(page, wrapper);
    }

}
