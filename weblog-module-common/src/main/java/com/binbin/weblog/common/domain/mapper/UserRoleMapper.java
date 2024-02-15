package com.binbin.weblog.common.domain.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.binbin.weblog.common.domain.dos.UserRoleDO;

import java.util.List;

public interface UserRoleMapper extends BaseMapper<UserRoleDO> {
    /**
     * 根据用户名查询
     * @param username
     */
    default List<UserRoleDO> selectByUsername(String username) {
        LambdaQueryWrapper<UserRoleDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRoleDO::getUsername, username);

        return selectList(wrapper);//满足.eq条件的所有记录，以列表形式返回
    }
}
