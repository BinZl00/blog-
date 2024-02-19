package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.binbin.weblog.common.domain.dos.CategoryDO;

import java.time.LocalDate;
import java.util.Objects;

public interface CategoryMapper extends BaseMapper<CategoryDO> {

    /**
     * 根据用户名查询
     */
    default CategoryDO selectByName(String categoryName) {
        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CategoryDO::getName, categoryName);//WHERE name = 'categoryName入参'

        // 执行查询
        return selectOne(wrapper);
    }

    /**
     * 查询分类分页数据
     */
    default Page<CategoryDO> selectPageList(long current, long size, String name, LocalDate startDate, LocalDate endDate) {
        // 分页对象(查询第几页、每页多少数据)
        Page<CategoryDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();

        wrapper.like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim()) // like 模块查询
                //如果startDate 不为null,并且Objects.nonNull(startDate) 为 true,启用应用ge查询。那么查询条件为createTime >= startDate。
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate) // 大于等于 startDate
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)  // 小于等于 endDate
                .orderByDesc(CategoryDO::getCreateTime); // 按创建时间倒叙
        //MyBatis Plu的selectPage方法返回一个IPage类的对象，它包含了分页查询的结果。
        return selectPage(page, wrapper);
    }
/** MyBatis Plus 提供的，它们是 Page 类的一部分。Page<CategoryDO>对象，它包含了以下信息：
 * getRecords()：返回当前页的数据列表，即CategoryDO对象的列表。
 * getCurrent()：返回当前页码。
 * getSize()：返回每页显示的记录数。
 * getTotal()：返回总记录数。
 * getPages()：返回总页数。
 */

}
