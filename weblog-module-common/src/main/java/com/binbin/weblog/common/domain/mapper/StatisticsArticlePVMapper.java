package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.binbin.weblog.common.domain.dos.StatisticsArticlePVDO;

import java.time.LocalDate;
import java.util.List;

public interface StatisticsArticlePVMapper extends BaseMapper<StatisticsArticlePVDO> {

    /**
     * 对指定日期的文章 PV 访问量进行 +1
     * @param date
     */
    default int increasePVCount(LocalDate date) {
        return update(null, Wrappers.<StatisticsArticlePVDO>lambdaUpdate()
                .setSql("pv_count = pv_count + 1")
                .eq(StatisticsArticlePVDO::getPvDate, date));
    }

    /**
     * 查询最近一周的文章 PV 访问量记录
     */
    default List<StatisticsArticlePVDO> selectLatestWeekRecords() {
        return selectList(Wrappers.<StatisticsArticlePVDO>lambdaQuery()
           //PvDate（文章PV访问日期）小于或等于明天的所有记录
                .le(StatisticsArticlePVDO::getPvDate, LocalDate.now().plusDays(1))
                .orderByDesc(StatisticsArticlePVDO::getPvDate) //降序
                .last("limit 7")); // 仅查询最近7天的数据
    }


}
