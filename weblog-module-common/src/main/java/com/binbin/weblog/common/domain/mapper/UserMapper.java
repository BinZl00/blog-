package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binbin.weblog.common.domain.dos.UserDO;

import java.time.LocalDateTime;

public interface UserMapper extends BaseMapper<UserDO> {
    //实现类没有重写默认接口的default方法，那么它将自动执行 继承接口中的默认方法
    default UserDO findByUsername(String username) {
        //查询构造器,构建条件 UserDO类的getUsername方法 = username
        LambdaQueryWrapper<UserDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserDO::getUsername, username); //相当于sql的where
        return selectOne(wrapper);
    }

    default int updatePasswordByUsername(String username, String password) {
        LambdaUpdateWrapper<UserDO> wrapper = new LambdaUpdateWrapper<>();
        // 设置要更新的字段
        wrapper.set(UserDO::getPassword, password);
        wrapper.set(UserDO::getUpdateTime, LocalDateTime.now());
        // 更新条件
        wrapper.eq(UserDO::getUsername, username);
        return update(null, wrapper); //传入null表示不使用实体类的主键进行更新
    }

}