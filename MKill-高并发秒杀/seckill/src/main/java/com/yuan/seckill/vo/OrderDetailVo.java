package com.yuan.seckill.vo;

import com.yuan.seckill.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @module:
 * @description: 订单详情返回对象
 * @author: yuan_boss
 * @create: 2022-08-23 15:48
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailVo {
    private Order order;
    private GoodsVo goodsVo;
}
