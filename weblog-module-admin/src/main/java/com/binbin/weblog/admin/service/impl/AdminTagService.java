package com.binbin.weblog.admin.service.impl;
import com.binbin.weblog.admin.model.vo.tag.AddTagReqVO;
import com.binbin.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     */
    Response addTags(AddTagReqVO addTagReqVO);
}
