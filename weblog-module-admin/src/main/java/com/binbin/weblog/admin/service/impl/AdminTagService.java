package com.binbin.weblog.admin.service.impl;
import com.binbin.weblog.admin.model.vo.tag.AddTagReqVO;
import com.binbin.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.binbin.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.binbin.weblog.common.utils.PageResponse;
import com.binbin.weblog.common.utils.Response;

public interface AdminTagService {

    /**
     * 添加标签集合
     */
    Response addTags(AddTagReqVO addTagReqVO);

    /**
     * 查询标签分页
     */
    PageResponse findTagPageList(FindTagPageListReqVO findTagPageListReqVO);

    /**
     * 删除标签
     */
    Response deleteTag(DeleteTagReqVO deleteTagReqVO);

}
