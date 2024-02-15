package com.binbin.weblog.jwt.service;

import com.binbin.weblog.common.domain.dos.UserDO;
import com.binbin.weblog.common.domain.dos.UserRoleDO;
import com.binbin.weblog.common.domain.mapper.UserMapper;
import com.binbin.weblog.common.domain.mapper.UserRoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 实现了Spring Security的UserDetailsService接口。这个接口用于在Spring Security中加载用户详细信息，通常在用户登录时被调用。
 */
@Service
@Slf4j
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 从数据库中查询
        UserDO userDO = userMapper.findByUsername(username);

        /*暂时先写死，密码为 quanxiaoha, 这里填写的密文，数据库中也是存储此种格式
        authorities 用于指定 角色，这里写死为 ADMIN 管理员
        return User.withUsername("quanxiaoha")
                .password("$2a$10$pGiLeqdqpoJ4ez68xkAWKOFER3yDKv3VXXbJj/FJvAhzSJ.AgNaJS")
                .authorities("ADMIN")
                .build();*/
        // 判断用户是否存在
        if (Objects.isNull(userDO)) {
            throw new UsernameNotFoundException("该用户不存在");//异常是Spring Security指示用户名不存在的异常
        }
        // 用户名关联的角色列表
        List<UserRoleDO> roleList = userRoleMapper.selectByUsername(username);
        String[] roleArr = null;//空字符串数组 roleArr，用于存储角色名称

        // 把这些角色名称提取出来，放到一个数组里
        if (!CollectionUtils.isEmpty(roleList)) {
            /*toArray(new String[roles.size()]) 是流（Stream）API的一个结果操作，它将流（Stream）中的元素收集到一个数组中。
            这个操作通常用于当你需要将流的结果转换为数组*/
            roleArr = roleList.stream().map(UserRoleDO::getRole).toArray(String[]::new); //new String[roles.size()] 创建一个初始化的新数组
        }

        log.info("角色有{ }"+ Arrays.toString(roleArr));

        // authorities 用于指定角色，authorities 角色字段，不再写死，改从数据库中查询，当角色不为空时，将其转换为数组传入
        return User.withUsername(userDO.getUsername())
                .password(userDO.getPassword())
                .authorities(roleArr)
                .build();
    }

}


