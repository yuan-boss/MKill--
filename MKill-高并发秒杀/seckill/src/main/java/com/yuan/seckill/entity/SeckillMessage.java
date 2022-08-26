package com.yuan.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @module:
 * @description: 秒杀信息
 * @author: yuan_boss
 * @create: 2022-08-24 16:22
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeckillMessage {
    private User user;
    private Long goodId;
}
