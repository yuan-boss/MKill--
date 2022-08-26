package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.Order;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.vo.GoodsVo;
import com.yuan.seckill.vo.OrderDetailVo;

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

    /**
     * @Author yuan_boss
     * @Description 订单详情
     * @Date 15:50 2022/8/23
     * @Param
     * @return
     **/
    OrderDetailVo detail(Long orderId);

    /**
     * @Author yuan_boss
     * @Description 获取秒杀地址
     * @Date 15:40 2022/8/25
     * @Param
     * @return
     **/
    String createPath(User user, Long goodsId);

    /**
     * @Author yuan_boss
     * @Description 校验秒杀地址
     * @Date 15:48 2022/8/25
     * @Param
     * @return
     **/
    boolean checkPath(User user, Long goodsId,String path);

    /**
     * @Author yuan_boss
     * @Description 校验验证码
     * @Date 9:08 2022/8/26
     * @Param
     * @return
     **/
    boolean checkCaptcha(User user, Long goodsId, String captcha);
}
