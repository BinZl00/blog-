package com.binbin.weblog.admin.service.impl;
import com.binbin.weblog.admin.model.vo.tag.AddTagReqVO;
import com.binbin.weblog.admin.model.vo.tag.DeleteTagReqVO;
import com.binbin.weblog.admin.model.vo.tag.FindTagPageListReqVO;
import com.binbin.weblog.admin.model.vo.tag.SearchTagsReqVO;
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

    /**
     * 根据标签关键词模糊查询
     */
    Response searchTags(SearchTagsReqVO searchTagsReqVO);

}
