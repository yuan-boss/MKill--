package com.yuan.seckill.rabbitmq;

import com.yuan.seckill.entity.SeckillMessage;
import com.yuan.seckill.entity.SeckillOrder;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IOrderService;
import com.yuan.seckill.utils.JsonUtil;
import com.yuan.seckill.vo.GoodsVo;
import com.yuan.seckill.vo.RespBean;
import com.yuan.seckill.vo.RespBeanEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @module:
 * @description: 消息接收者
 * @author: yuan_boss
 * @create: 2022-08-24 11:29
 **/
@Service
@Slf4j
public class MQReceiver {

//    @RabbitListener(queues = "queue")
//    public void receive(Object msg){
//        log.info("接收消息："+msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout01")
//    public void receive01(Object msg){
//        log.info("接收消息QUEUE01{}",msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout02")
//    public void receive02(Object msg){
//        log.info("接收消息QUEUE02{}",msg);
//    }
//
//    @RabbitListener(queues = "queue_direct01")
//    public void receive03(Object msg){
//        log.info("接收消息QUEUE01{}",msg);
//    }
//
//    @RabbitListener(queues = "queue_direct02")
//    public void receive04(Object msg){
//        log.info("接收消息QUEUE02{}",msg);
//    }
//    @RabbitListener(queues = "queue_topic01")
//    public void receive05(Object msg){
//        log.info("接收消息QUEUE01{}",msg);
//    }
//    @RabbitListener(queues = "queue_topic02")
//    public void receive06(Object msg){
//        log.info("接收消息QUEUE02{}",msg);
//    }
//
//    @RabbitListener(queues = "queue_header01")
//    public void receive07(Message message){
//        log.info("QUEUE01接收Message对象{}",message);
//        log.info("QUEUE01接收消息{}",new String(message.getBody()));
//    }
//    @RabbitListener(queues = "queue_header02")
//    public void receive08(Message message){
//        log.info("QUEUE02接收Message对象{}",message);
//        log.info("QUEUE02接收消息{}",new String(message.getBody()));
//    }

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private IOrderService orderService;

    /**
     * @Author yuan_boss
     * @Description 下单操作
     * @Date 16:34 2022/8/24
     * @Param
     * @return
     **/
    @RabbitListener(queues = "seckillQueue")
    public void receive(String message){
        log.info("接收到的消息：{}",message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
        Long goodId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();
        //判断库存
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodId);
        if (goodsVo.getStockCount() < 1){
            return;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodId);

        if (seckillOrder != null) {
            return;
        }
        //下单操作
        orderService.seckill(user,goodsVo);




    }

}
