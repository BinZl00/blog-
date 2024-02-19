package com.binbin.weblog.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.binbin.weblog.admin.model.vo.tag.AddTagReqVO;
import com.binbin.weblog.common.domain.dos.TagDO;
import com.binbin.weblog.common.domain.mapper.TagMapper;
import com.binbin.weblog.common.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j // ServiceImpl 是MyBatis Plus提供的一个通用服务类,实现了一些基本的数据库操作
public class AdminTagServiceImpl extends ServiceImpl<TagMapper, TagDO> implements AdminTagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 添加标签集合
     */
    @Override
    public Response addTags(AddTagReqVO addTagReqVO) {
        // vo 转 do
        List<TagDO> tagDOS = addTagReqVO.getTags().stream()
                .map(i -> TagDO.builder()
                        .name(i.trim()) // 去掉前后空格
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        // 批量插入
        try {
            saveBatch(tagDOS); //调用了ServiceImpl 提供的 saveBatch 方法
        } catch (Exception e) {
            log.warn("该标签已存在", e); //已存在可以忽略
        }

        return Response.success();
    }
}
