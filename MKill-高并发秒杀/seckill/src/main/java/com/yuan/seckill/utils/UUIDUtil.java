package com.yuan.seckill.utils;

import java.util.UUID;

/**
 * @module:
 * @description: UUID工具类
 * @author: yuan_boss
 * @create: 2022-08-19 17:13
 **/
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
