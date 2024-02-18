package com.binbin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.binbin.weblog.admin.model.vo.FindCategoryPageListReqVO;
import com.binbin.weblog.admin.model.vo.FindCategoryPageListRspVO;
import com.binbin.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.binbin.weblog.common.domain.dos.CategoryDO;
import com.binbin.weblog.common.domain.mapper.CategoryMapper;
import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.exception.BizException;
import com.binbin.weblog.common.utils.PageResponse;
import com.binbin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminCategoryServiceImpl implements AdminCategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加分类
     */
    @Override
    public Response addCategory(AddCategoryReqVO addCategoryReqVO) {
        String categoryName = addCategoryReqVO.getName();

        // 先判断该分类是否已经存在
        CategoryDO categoryDO = categoryMapper.selectByName(categoryName);

        if (Objects.nonNull(categoryDO)) {
            log.warn("分类名称： {}, 此分类已存在", categoryName);
            throw new BizException(ResponseCodeEnum.CATEGORY_NAME_IS_EXISTED);
        }

        // 构建 插入后端数据库的 DO 类
        CategoryDO insertCategoryDO = CategoryDO.builder()
                .name(addCategoryReqVO.getName().trim())
                .build();

        // 执行 insert
        categoryMapper.insert(insertCategoryDO);

        return Response.success();
    }

    @Override
    public PageResponse findCategoryList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 获取前端入参数据，当前页、每页展示的数据量等
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();
        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();

        // 分页对象(查询第几页、每页多少数据)
        Page<CategoryDO> page = new Page<>(current, size);

        // 构建查询条件
        LambdaQueryWrapper<CategoryDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotBlank(name), CategoryDO::getName, name.trim()) // like 模块查询
             //如果startDate 不为null,并且Objects.nonNull(startDate) 为 true,启用应用ge查询。那么查询条件为createTime >= startDate。
                .ge(Objects.nonNull(startDate), CategoryDO::getCreateTime, startDate) // 大于等于 startDate
                .le(Objects.nonNull(endDate), CategoryDO::getCreateTime, endDate)  // 小于等于 endDate
                .orderByDesc(CategoryDO::getCreateTime); // 按创建时间倒叙

        // selectPage(分页对象，查询条件)执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPage(page, wrapper);
        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();
/** MyBatis Plus 提供的，它们是 Page 类的一部分。Page<CategoryDO>对象，它包含了以下信息：
 * getRecords()：返回当前页的数据列表，即CategoryDO对象的列表。
 * getCurrent()：返回当前页码。
 * getSize()：返回每页显示的记录数。
 * getTotal()：返回总记录数。
 * getPages()：返回总页数。
 */
        // List<CategoryDO>（数据库的实体列表）转换List<FindCategoryPageListRspVO>（响应前端的视图对象列表）
        List<FindCategoryPageListRspVO> vos = null;

        if (!CollectionUtils.isEmpty(categoryDOS)) {
            vos  = categoryDOS.stream()
                    .map(i -> FindCategoryPageListRspVO.builder()
                            .id(i.getId())
                            .name(i.getName())
                            .createTime(i.getCreateTime())
                            .build())
                    .collect(Collectors.toList());
        }

        return PageResponse.success(categoryDOPage, vos);
    }
}