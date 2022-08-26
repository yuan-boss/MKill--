package com.yuan.seckill.controller;

import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.vo.DetailVo;
import com.yuan.seckill.vo.GoodsVo;
import com.yuan.seckill.vo.RespBean;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Date;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * @Author yuan_boss
     * @Description 跳转到商品列表页
     * @Date 17:54 2022/8/19
     * @Param
     * @return
     **/
    @RequestMapping(value = "/toList",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toList(Model model, User user,HttpServletRequest request, HttpServletResponse response){
//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute("ticket");
//        User user = userService.getUserByCookie(ticket,request,response);
//        if (null == user){
//            return "login";
//        }
        //Redis中获取页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        model.addAttribute("user",user);
        model.addAttribute("goodsList",goodsService.findGoodVo());
        //        return "goodsList";
        //如果为空，手动渲染，存入redis并返回
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList",context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList",html,60, TimeUnit.SECONDS);
        }
        return html;
    }

    /**
     * @Author yuan_boss
     * @Description 跳转到商品详情页
     * @Date 17:54 2022/8/19
     * @Param
     * @return
     **/
    @RequestMapping(value = "/toDetail2/{goodsId}",produces = "text/html;charset=utf-8")
    @ResponseBody
    public String toDetails2(Model model,User user,@PathVariable Long goodsId,HttpServletRequest request, HttpServletResponse response){
        //redis中获取页面，如果不为空，直接返回页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String)valueOperations.get("goodsDetails:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
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
        //如果为空，手动渲染，存入redis并返回
        WebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if(!StringUtils.isEmpty(html)){
            valueOperations.set("goodsDetails:" + goodsId,html,60,TimeUnit.MINUTES);
        }

//        return "goodsDetail";
        return html;
    }


    /**
     * @Author yuan_boss
     * @Description 获取商品详情页的对象
     * @Date 10:13 2022/8/23
     * @Param
     * @return
     **/
    @RequestMapping(value = "/detail/{goodsId}")
    @ResponseBody
    public RespBean toDetails(User user, @PathVariable Long goodsId){

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
        DetailVo detailVo = new DetailVo();
        detailVo.setUser(user);
        detailVo.setGoodsVo(goodsVo);
        detailVo.setSecKillStatus(secKillStatus);
        detailVo.setRemainSeconds(remainSeconds);

        return RespBean.success(detailVo);
    }

}
