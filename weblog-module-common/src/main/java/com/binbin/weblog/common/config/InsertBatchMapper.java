package com.binbin.weblog.common.config;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface InsertBatchMapper<T> extends BaseMapper<T> {

    // 批量插入
    int insertBatchSomeColumn(@Param("list") List<T> batchList);
/**
 @Param("list") 注解将 batchList 参数命名为 list。
 别名随后可以在 XML 文件中的 <foreach  collection="list"> 中使用
 背后隐藏了自动化生成 SQL 和高效执行数据库操作的能力
 */
}