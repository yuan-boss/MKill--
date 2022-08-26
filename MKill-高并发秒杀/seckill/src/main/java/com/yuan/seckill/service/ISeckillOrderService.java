package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.SeckillOrder;
import com.yuan.seckill.entity.User;

/**
 * <p>
 * 秒杀订单表 服务类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
public interface ISeckillOrderService extends IService<SeckillOrder> {

    /**
     * @Author yuan_boss
     * @Description 获取秒杀结果
     * @Date 8:48 2022/8/25
     * @Param
     * @return orderId:成功，-1:秒杀失败，0:排队中
     **/
    Long getResult(User user, Long goodsId);
}
