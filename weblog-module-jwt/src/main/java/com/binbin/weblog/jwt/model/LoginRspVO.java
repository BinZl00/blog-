package com.binbin.weblog.jwt.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRspVO {//Rsp 是 response 的缩写，返参

    /**
     * Token 值
     */
    private String token;

}