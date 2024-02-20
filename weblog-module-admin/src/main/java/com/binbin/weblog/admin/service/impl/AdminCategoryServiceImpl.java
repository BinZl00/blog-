package com.binbin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.binbin.weblog.admin.model.vo.category.FindCategoryPageListReqVO;
import com.binbin.weblog.admin.model.vo.category.FindCategoryPageListRspVO;
import com.binbin.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.binbin.weblog.admin.model.vo.category.DeleteCategoryReqVO;
import com.binbin.weblog.common.domain.dos.CategoryDO;
import com.binbin.weblog.common.domain.mapper.CategoryMapper;
import com.binbin.weblog.common.enums.ResponseCodeEnum;
import com.binbin.weblog.common.exception.BizException;
import com.binbin.weblog.common.model.vo.SelectRspVO;
import com.binbin.weblog.common.utils.PageResponse;
import com.binbin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
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

    //添加分类
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

    //分页
    @Override
    public PageResponse findCategoryPageList(FindCategoryPageListReqVO findCategoryPageListReqVO) {
        // 获取前端入参数据，当前页、每页展示的数据量等
        Long current = findCategoryPageListReqVO.getCurrent();
        Long size = findCategoryPageListReqVO.getSize();
        String name = findCategoryPageListReqVO.getName();
        LocalDate startDate = findCategoryPageListReqVO.getStartDate();
        LocalDate endDate = findCategoryPageListReqVO.getEndDate();

        // 执行分页查询
        Page<CategoryDO> categoryDOPage = categoryMapper.selectPageList(current, size, name, startDate, endDate);

        List<CategoryDO> categoryDOS = categoryDOPage.getRecords();

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

    //删除分类
    @Override
    public Response deleteCategory(DeleteCategoryReqVO deleteCategoryReqVO) {
        // 分类 ID
        Long categoryId = deleteCategoryReqVO.getId();
        // 删除分类
        categoryMapper.deleteById(categoryId);
        return Response.success();
    }

    //下拉菜单分类
    @Override
    public Response findCategorySelectList() {
        // 查询所有分类，null 参数表示不使用任何查询条件。。
        List<CategoryDO> categoryDOS = categoryMapper.selectList(null);

        // DO 转 VO
        List<SelectRspVO> selectRspVOS = null;
        // 如果分类数据不为空
        if (!CollectionUtils.isEmpty(categoryDOS)) {
            // 将分类 ID 作为 Value 值，将分类名称作为 label 展示
            selectRspVOS = categoryDOS.stream()
                    .map(categoryDO -> SelectRspVO.builder()
                            .label(categoryDO.getName())
                            .value(categoryDO.getId())
                            .build())
                    .collect(Collectors.toList());
        }

        return Response.success(selectRspVOS);
    }


}