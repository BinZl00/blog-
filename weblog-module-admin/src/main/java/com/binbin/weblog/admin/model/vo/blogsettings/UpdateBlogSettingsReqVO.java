package com.binbin.weblog.admin.model.vo.blogsettings;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = " 博客基础信息修改入参 VO")
public class UpdateBlogSettingsReqVO {

    @NotBlank(message = "博客 LOGO 不能为空")
    private String logo;

    @NotBlank(message = "博客名称不能为空")
    private String name;

    @NotBlank(message = "博客作者不能为空")
    private String author;

    @NotBlank(message = "博客介绍语不能为空")
    private String introduction;

    @NotBlank(message = "博客头像不能为空")
    private String avatar;

    private String githubHomepage;

    private String csdnHomepage;

    private String giteeHomepage;

    private String zhihuHomepage;
}

/**
 {
     "author": "", // 作者
     "avatar": "", // 作者头像
     "introduction": "", // 介绍语
     "logo": "", // 博客 LOGO
     "name": "", // 博客名称
     "csdnHomepage": "", // csdn 主页地址
     "giteeHomepage": "", // gitee 主页地址
     "githubHomepage": "", // github 主页地址
     "zhihuHomepage": "" // 知乎主页地址
 }
 */