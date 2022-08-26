package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.SeckillOrder;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.exception.GlobalException;
import com.yuan.seckill.mapper.OrderMapper;
import com.yuan.seckill.mapper.SeckillOrderMapper;
import com.yuan.seckill.service.ISeckillOrderService;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀订单表 服务实现类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
@Service
public class SeckillOrderServiceImpl extends ServiceImpl<SeckillOrderMapper, SeckillOrder> implements ISeckillOrderService {

    @Autowired
    private SeckillOrderMapper seckillOrderMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Author yuan_boss
     * @Description 获取秒杀结果
     * @Date 8:48 2022/8/25
     * @Param
     * @return orderId:成功，-1:秒杀失败，0:排队中
     **/
    @Override
    public Long getResult(User user, Long goodsId) {
        if (goodsId == null) {
            throw new GlobalException(RespBeanEnum.EMPTY_SECKILLGOOD);
        }
        SeckillOrder seckillOrder = seckillOrderMapper.selectOne(new QueryWrapper<SeckillOrder>()
                .eq("user_id", user.getId())
                .eq("goods_id", goodsId)
        );
        if (null != seckillOrder){
            return seckillOrder.getOrderId();
        } else if (redisTemplate.hasKey("isStockEmpty:" + goodsId)) {
            return -1L;
        }else {
            return 0L;
        }

    }
}
