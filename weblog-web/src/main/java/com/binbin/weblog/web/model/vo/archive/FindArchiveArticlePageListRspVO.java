package com.binbin.weblog.web.model.vo.archive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArchiveArticlePageListRspVO { //返回前台归档页信息 主类
    /**
     * 归档的月份
     */
    private YearMonth month;

    //子类
    private List<FindArchiveArticleRspVO> articles;

}