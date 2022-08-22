package com.yuan.seckill.controller;

import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.vo.GoodsVo;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;

import static net.sf.jsqlparser.util.validation.metadata.NamedObject.user;

/**
 * @module:
 * @description: 商品
 * @author: yuan_boss
 * @create: 2022-08-19 17:49
 **/
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IGoodsService goodsService;

    /**
     * @Author yuan_boss
     * @Description 跳转到商品列表页
     * @Date 17:54 2022/8/19
     * @Param
     * @return
     **/
    @RequestMapping("/toList")
    public String toList(Model model, User user){
//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute("ticket");
//        User user = userService.getUserByCookie(ticket,request,response);
//        if (null == user){
//            return "login";
//        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodVo());
        return "goodsList";
    }

    /**
     * @Author yuan_boss
     * @Description 跳转到商品详情页
     * @Date 17:54 2022/8/19
     * @Param
     * @return
     **/
    @RequestMapping("/toDetail/{goodsId}")
    public String toDetails(Model model,User user,@PathVariable Long goodsId){
        model.addAttribute("user",user);
        GoodsVo goodsVo = goodsService.findGoodsVoByGoodsId(goodsId);
        Date startDate = goodsVo.getStartDate();
        Date endDate = goodsVo.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int secKillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;

        //秒杀还没开始
        if (nowDate.before(startDate)){
            secKillStatus = 0;
            remainSeconds =  (int)((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {//秒杀已结束
            secKillStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            secKillStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("remainSeconds",remainSeconds);
        model.addAttribute("secKillStatus",secKillStatus);
        model.addAttribute("goods",goodsVo);
        return "goodsDetail";
    }

}
