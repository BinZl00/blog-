package com.binbin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.binbin.weblog.admin.model.vo.dashboard.FindDashboardStatisticsInfoRspVO;
import com.binbin.weblog.common.domain.dos.ArticleDO;
import com.binbin.weblog.common.domain.dos.ArticlePublishCountDO;
import com.binbin.weblog.common.domain.mapper.ArticleMapper;
import com.binbin.weblog.common.domain.mapper.CategoryMapper;
import com.binbin.weblog.common.domain.mapper.TagMapper;
import com.binbin.weblog.common.utils.Response;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminDashboardServiceImpl implements AdminDashboardService {

    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private TagMapper tagMapper;

    /**
     * 获取仪表盘基础统计信息
     */
    @Override
    public Response findDashboardStatistics() {
        // 查询文章表记录总数
        Long articleTotalCount = articleMapper.selectCount(Wrappers.emptyWrapper());

        // 查询分类总数
        Long categoryTotalCount = categoryMapper.selectCount(Wrappers.emptyWrapper());

        // 查询标签总数
        Long tagTotalCount = tagMapper.selectCount(Wrappers.emptyWrapper());

        // 总浏览量
        List<ArticleDO> articleDOS = articleMapper.selectAllReadNum();
        Long pvTotalCount = 0L;

        if (!CollectionUtils.isEmpty(articleDOS)) {
            // 所有 read_num 相加 .mapToLong操作Long型
            pvTotalCount = articleDOS.stream().mapToLong(ArticleDO::getReadNum).sum();
        }

        // 组装 VO 类
        FindDashboardStatisticsInfoRspVO vo = FindDashboardStatisticsInfoRspVO.builder()
                .articleTotalCount(articleTotalCount)
                .categoryTotalCount(categoryTotalCount)
                .tagTotalCount(tagTotalCount)
                .pvTotalCount(pvTotalCount)
                .build();

        return Response.success(vo);
    }

    /**
     * 获取过去一年内每天文章发布的统计信息
     */
    @Override
    public Response findDashboardPublishArticleStatistics() {
        // 当前日期
        LocalDate currDate = LocalDate.now();

        // 当前日期倒退一年的日期
        LocalDate startDate = currDate.minusYears(1);

        //查询的时间区间是从一年前的今天到今天结束（不包括明天）。currDate.plusDays(1)加上一天是为了确保包含 currDate 这一天的数据
        List<ArticlePublishCountDO> articlePublishCountDOS = articleMapper.selectDateArticlePublishCount(startDate, currDate.plusDays(1));

        Map<LocalDate, Long> map = null;
        if (!CollectionUtils.isEmpty(articlePublishCountDOS)) {
            // DO 转 Map
            Map<LocalDate, Long> dateArticleCountMap = articlePublishCountDOS.stream()
                    .collect(Collectors.toMap(ArticlePublishCountDO::getDate, ArticlePublishCountDO::getCount));

            // 有序 Map, 插入顺序，返回的日期文章数需要以升序（从小到大）排列
            map = Maps.newLinkedHashMap();
         /* 从上一年的今天循环到今天，包含每一天的文章发布数量。某一天没有文章发布，这一天的计数为 0
            for条件部分检查startDate是否在 currDate 之前 或 等于 currDate 。startDate.plusDays(1)加1天 */
            for ( ; startDate.isBefore(currDate) || startDate.isEqual(currDate); startDate = startDate.plusDays(1)) {
                // 以日期作为 key 从 dateArticleCountMap 中取文章发布总量
                Long count = dateArticleCountMap.get(startDate);//键拿值
                // 设置到返参 Map
                map.put(startDate, Objects.isNull(count) ? 0 : count);
            }
        }

        return Response.success(map);
    }

}
