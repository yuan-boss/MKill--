package com.yuan.seckill.vo;

import com.yuan.seckill.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @module:
 * @description: 详情返回对象
 * @author: yuan_boss
 * @create: 2022-08-23 10:09
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailVo {
    private User user;
    private  GoodsVo goodsVo;
    private int secKillStatus;
    private int remainSeconds;
}
