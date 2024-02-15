package com.binbin.weblog.web;

import com.binbin.weblog.common.domain.dos.UserDO;
import com.binbin.weblog.common.domain.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
@Slf4j
class WeblogWebApplicationTests {

    @Test
    void testLog(){
        log.info("info级别日志");
        log.warn("warn级别日志");
        log.error("error级别日志");

        String my= "斌仔";
        log.info("日志，作者；{}", my);
    }

    @Autowired
    private UserMapper userMapper;

    @Test
    void insertTest() {
        // 构建数据库实体类
        UserDO userDO = UserDO.builder()
                .username("犬小哈")
                .password("123456")
                .createTime(new Date())
                .updateTime(new Date())
                .isDeleted(false)
                .build();

        userMapper.insert(userDO);
    }

}
