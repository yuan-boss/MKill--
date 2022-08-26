package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.*;
import com.yuan.seckill.exception.GlobalException;
import com.yuan.seckill.mapper.OrderMapper;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IOrderService;
import com.yuan.seckill.service.ISeckillGoodsService;
import com.yuan.seckill.service.ISeckillOrderService;
import com.yuan.seckill.utils.MD5Util;
import com.yuan.seckill.utils.UUIDUtil;
import com.yuan.seckill.vo.DetailVo;
import com.yuan.seckill.vo.GoodsVo;
import com.yuan.seckill.vo.OrderDetailVo;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.nio.file.Path;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Author yuan_boss
     * @Description 秒杀
     * @Date 9:44 2022/8/22
     * @Param
     * @return
     **/
    @Transactional
    @Override
    public Order seckill(User user, GoodsVo goods) {
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //更新秒杀商品的库存
        SeckillGoods seckillGoods = seckillGoodsService.getOne(new QueryWrapper<SeckillGoods>().eq("goods_id", goods.getId()));
        seckillGoods.setStockCount(seckillGoods.getStockCount() - 1);
//        seckillGoodsService.updateById(seckillGoods);
        boolean result = seckillGoodsService.update(new UpdateWrapper<SeckillGoods>()
                .setSql("stock_count = stock_count - 1")//设置秒杀商品的库存
                .eq("goods_id", goods.getId())//确保id一致
                .gt("stock_count", 0)//确保秒杀库存大于0
        );
//        if (!result){
//            return null;
//        }
        if (seckillGoods.getStockCount() < 1) {
            //判断是否有库存
            //这里的值随便设置，因为只需要判断是否有这个key即可
            valueOperations.set("isStockEmpty:" + goods.getId(),"0");
            return null;
        }
        //更新商品的库存
        Goods goods1 = goodsService.getOne(new QueryWrapper<Goods>().eq("id", goods.getId()));
        goods1.setGoodsStock(goods1.getGoodsStock() - 1);
//        goodsService.updateById(goods1);
        boolean goodsResult = goodsService.update(new UpdateWrapper<Goods>()
                .setSql("goods_stock = goods_stock - 1")
                .eq("id", goods.getId())
                .gt("goods_stock", 0)
        );
        if (!goodsResult){
            return null;
        }


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
        //将秒杀订单存入redis
        redisTemplate.opsForValue().set("order:"+user.getId()+":"+goods.getId(),seckillOrder);

        return order;
    }

    /**
     * @Author yuan_boss
     * @Description 获取订单详情
     * @Date 15:51 2022/8/23
     * @Param
     * @return
     **/
    @Override
    public OrderDetailVo detail(Long orderId) {
        if (orderId == null){
            throw new GlobalException(RespBeanEnum.ORDER_NOT_EXIST);
        }
        Order order = orderMapper.selectById(orderId);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(order.getGoodsId());
        OrderDetailVo detail = new OrderDetailVo();
        detail.setOrder(order);
        detail.setGoodsVo(goodsVo);
        return detail;

    }

    /**
     * @Author yuan_boss
     * @Description 获取秒杀地址
     * @Date 15:41 2022/8/25
     * @Param
     * @return
     **/
    @Override
    public String createPath(User user, Long goodsId) {
        if (goodsId == null) {
            throw new GlobalException(RespBeanEnum.EMPTY_SECKILLGOOD);
        }
        String str = MD5Util.md5(UUIDUtil.uuid() + "123456");
        redisTemplate.opsForValue().set("seckillPath:"+user.getId()+":"+goodsId,str,60, TimeUnit.SECONDS);
        return str;
    }

    /**
     * @Author yuan_boss
     * @Description 校验秒杀地址
     * @Date 15:48 2022/8/25
     * @Param
     * @return
     **/
    @Override
    public boolean checkPath(User user, Long goodsId, String path) {
        if (user == null || goodsId < 0 || StringUtils.isEmpty(path)) {
            return false;
        }
        String redisPath = (String) redisTemplate.opsForValue().get("seckillPath:" + user.getId() + ":" + goodsId);

        return path.equals(redisPath);
    }

    /**
     * @Author yuan_boss
     * @Description 校验验证码
     * @Date 9:09 2022/8/26
     * @Param
     * @return
     **/
    @Override
    public boolean checkCaptcha(User user, Long goodsId, String captcha) {
        if (StringUtils.isEmpty(captcha) || user == null || goodsId < 0){
            return false;
        }
        String redisCaptcha = (String) redisTemplate.opsForValue().get("captcha:" + user.getId() + ":" + goodsId);

        return captcha.equals(redisCaptcha);
    }
}
