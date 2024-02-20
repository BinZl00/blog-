package com.binbin.weblog.admin.service.impl;

import com.binbin.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.binbin.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 更新博客设置信息
     */
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);

}
