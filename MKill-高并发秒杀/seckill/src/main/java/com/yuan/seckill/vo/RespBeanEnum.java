package com.yuan.seckill.vo;

import lombok.*;

/**
 * @module:
 * @description: 公共返回对象枚举
 * @author: yuan_boss
 * @create: 2022-08-19 14:23
 **/
@Getter
@AllArgsConstructor
public enum RespBeanEnum {

    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    //登录模块5002XX
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式不正确"),
    BIND_ERRO(500212,"参数校验异常"),
    //秒杀模块500XX
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品每人限购一件")

    ;

    private final Integer code;
    private final String message;

}
