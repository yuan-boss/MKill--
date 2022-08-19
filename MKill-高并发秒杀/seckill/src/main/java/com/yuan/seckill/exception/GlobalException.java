package com.yuan.seckill.exception;

import com.yuan.seckill.utils.vo.RespBeanEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @module:
 * @description: 全局异常
 * @author: yuan_boss
 * @create: 2022-08-19 16:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GlobalException extends RuntimeException{
    private RespBeanEnum respBeanEnum;

}
