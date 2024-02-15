package com.binbin.weblog.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
/*  ObjectMapper是Jackson库中的类，它主要用于处理JSON数据的序列化（将Java对象转换为JSON字符串）
    和反序列化（将JSON字符串转换为Java对象）。 */
    private static final ObjectMapper INSTANCE = new ObjectMapper(); //ObjectMapper 实例

    public static String toJsonString(Object obj) {//对象转换成 JSON 格式的字符串
        try {
            return INSTANCE.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return obj.toString(); //确保在序列化失败时至少能返回一个字符串表示的对象信息，而不是让程序直接崩溃
        }
    }

}