package com.binbin.weblog.admin.convert;

import com.binbin.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.binbin.weblog.common.domain.dos.BlogSettingsDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BlogSettingsConvert {
    /**
     INSTANCE静态实例用于在不同类型（例如 VO 到 DO 或者 DO 到 DTO）之间进行转换。
     每个类都有一个对应的 Class 对象，这个对象包含了类的元数据信息。通过 .class可以指向 BlogSettingsConvert类的引用。
     .getMapper()，该方法会根据这个类生成映射器的实现类，并返回这个实现类的实例。
     */
    BlogSettingsConvert INSTANCE = Mappers.getMapper(BlogSettingsConvert.class);

    /**
     * 将 VO 转化为 DO，博客基础信息修改入参UpdateBlogSettingsReqVO 转换为 表BlogSettingsDO对象
     */
    BlogSettingsDO convertVO2DO(UpdateBlogSettingsReqVO bean);
}
/*
类内部定义了INSTANCE，直接使用BlogSettingsConvert.INSTANCE.convertVO2DO(?)
不用每次调用，都需要获取映射器实例
BlogSettingsConvert a = Mappers.getMapper(BlogSettingsConvert.class);
*/
