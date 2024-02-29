package com.binbin.weblog.common.constant;

import java.time.format.DateTimeFormatter;

public interface Constants {//全局的静态变量
    /**
     * 月-日 格式，格式化器来格式化日期
     */
    DateTimeFormatter MONTH_DAY_FORMATTER = DateTimeFormatter.ofPattern("MM-dd");

}