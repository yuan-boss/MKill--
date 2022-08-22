package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.*;
import com.yuan.seckill.mapper.OrderMapper;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IOrderService;
import com.yuan.seckill.service.ISeckillGoodsService;
import com.yuan.seckill.service.ISeckillOrderService;
import com.yuan.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Autowired
    private ISeckillGoodsService seckillGoodsService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private OrderMapper orderMapper;
    /**
     * @Author yuan_boss
     * @Description 秒杀
     * @Date 9:44 2022/8/22
     * @Param
     * @return
     **/
    @Override
    public Order seckill(User user, GoodsVo goods) {
        //更新秒杀商品的库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
        seckillGoodsService.updateById(seckillGoods);
        //更新商品的库存
        Goods goods1 = goodsService.getOne(new QueryWrapper<Goods>().eq("id", goods.getId()));
        goods1.setGoodsStock(goods1.getGoodsStock() - 1);
        goodsService.updateById(goods1);


        //生成订单
        Order order = new Order();
        order.setUserId(user.getId());
        order.setGoodsId(goods.getId());
        order.setDeliveryAddrId(0L);
        order.setGoodsName(goods.getGoodsName());
        order.setGoodsCount(1);
        order.setGoodsPrice(seckillGoods.getSeckillPrice());
        order.setOrderChannel(1);
        order.setStatus(0);
        order.setCreateDate(new Date());
        orderMapper.insert(order);
        //生成秒杀订单
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setUserId(user.getId());
        seckillOrder.setOrderId(order.getId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrderService.save(seckillOrder);

        return order;
    }
}
