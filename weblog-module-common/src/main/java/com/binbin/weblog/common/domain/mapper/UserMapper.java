package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binbin.weblog.common.domain.dos.UserDO;

public interface UserMapper extends BaseMapper<UserDO> {
    //实现类没有重写默认接口的default方法，那么它将自动执行 继承接口中的默认方法
    default UserDO findByUsername(String username) {
        //查询构造器,构建条件 UserDO类的getUsername方法 = username
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username); //相当于sql的where
        return selectOne(wrapper);
    }

}