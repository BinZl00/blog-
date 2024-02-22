package com.binbin.weblog.common.config;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

public class InsertBatchSqlInjector extends DefaultSqlInjector {
//自定义方法注入器DefaultSqlInjector为 Mapper 接口中的自定义方法生成相应的 SQL 语句
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        // super.getMethodList() 保留 Mybatis Plus 自带的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        // 添加自定义方法：批量插入InsertBatchSomeColumn对象（这是一个在 配置类中 自定义的批量插入方法）
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }

}
