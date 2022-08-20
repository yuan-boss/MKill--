package com.yuan.seckill.controller;

import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IUserService;
import net.sf.jsqlparser.util.validation.metadata.NamedObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        return "goodsList";
    }

    /**
     * @Author yuan_boss
     * @Description 跳转到商品详情页
     * @Date 17:54 2022/8/19
     * @Param
     * @return
     **/
    @RequestMapping("/toDetails")
    public String toDetails(Model model, User user){
//        if (StringUtils.isEmpty(ticket)){
//            return "login";
//        }
////        User user = (User) session.getAttribute("ticket");
//        User user = userService.getUserByCookie(ticket,request,response);
//        if (null == user){
//            return "login";
//        }
        model.addAttribute("user",user);
        return "goodsList";
    }

}
