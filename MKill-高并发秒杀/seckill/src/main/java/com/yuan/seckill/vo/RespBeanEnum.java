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
    MOBILE_NOT_EXIST(500213,"手机号码不存在"),
    PASSWORD_UPDATE_FAIL(500214,"更新密码失败"),
    SESSION_ERROR(500215,"用户不存在"),

    //秒杀模块5005XX
    EMPTY_STOCK(500500,"库存不足"),
    REPEAT_ERROR(500501,"该商品每人限购一件"),
    EMPTY_SECKILLGOOD(500502,"秒杀商品ID为空"),
    REQUEST_ILLEGAL(500503,"请求非法,请重新尝试"),
    ERROR_CAPTCHA(500504,"验证码错误，请重新输入"),
    ACCESS_LIMIT_REACHED(500505,"访问过于频繁，请稍后再试"),

    //订单模块5003XX
    ORDER_NOT_EXIST(500300,"订单信息不存在")
    ;

    private final Integer code;
    private final String message;

}
