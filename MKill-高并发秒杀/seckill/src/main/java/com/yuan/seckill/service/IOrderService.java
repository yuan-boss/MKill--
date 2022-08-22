package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.Order;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
public interface IOrderService extends IService<Order> {

    /**
     * @Author yuan_boss
     * @Description 秒杀
     * @Date 9:44 2022/8/22
     * @Param
     * @return
     **/
    Order seckill(User user, GoodsVo goods);
}
