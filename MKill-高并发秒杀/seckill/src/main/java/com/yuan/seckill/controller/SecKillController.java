package com.yuan.seckill.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yuan.seckill.entity.Order;
import com.yuan.seckill.entity.SeckillOrder;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IOrderService;
import com.yuan.seckill.service.ISeckillOrderService;
import com.yuan.seckill.vo.GoodsVo;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @module:
 * @description: 秒杀
 * @author: yuan_boss
 * @create: 2022-08-22 09:28
 **/

@Controller
@RequestMapping("/secKill")
public class SecKillController {

    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private ISeckillOrderService seckillOrderService;
    @Autowired
    private IOrderService orderService;

    /**
     * @Author yuan_boss
     * @Description 秒杀
     * @Date 9:30 2022/8/22
     * @Param
     * @return
     **/
    @RequestMapping("/doSeckill")
    public String doSeckill(Model model, User user,Long goodsId){
        if (user == null){
            return "login";
        }
        model.addAttribute("user",user);
        GoodsVo goods = goodsService.findGoodsVoByGoodsId(goodsId);
        //判断库存
        if (goods.getStockCount() < 1){
            model.addAttribute("errmsg", RespBeanEnum.EMPTY_STOCK);
            return "secKillFail";
        }
        //判断是否重复抢购
        SeckillOrder secKillOrder = seckillOrderService.getOne(new QueryWrapper<SeckillOrder>().eq("user_id", user.getId()).eq("goods_id", goodsId));
        if (secKillOrder != null) {
            model.addAttribute("errmsg",RespBeanEnum.REPEAT_ERROR);
            return "secKillFail";
        }

        Order order = orderService.seckill(user,goods);
        model.addAttribute("order",order);
        model.addAttribute("goods",goods);
        return "orderDetail";


    }

}
