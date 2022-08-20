package com.yuan.seckill.controller;

import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.utils.vo.LoginVo;
import com.yuan.seckill.utils.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @module:
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-18 10:41
 **/
@Controller
@RequestMapping("/login")
@Slf4j
public class LoginController {
    @Autowired
    private IUserService userService;

    /**
     * @Author yuan_boss
     * @Description  跳转登录页面
     * @Date 10:46 2022/8/18
     * @Param
     * @return
     **/
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }

    /**
     * @Author yuan_boss
     * @Description  登录功能
     * @Date 14:48 2022/8/19
     * @Param loginVo 接收前端的登录参数
     * @return
     **/
    @RequestMapping("/doLogin")
    @ResponseBody
    public RespBean doLogin(@Valid LoginVo loginVo, HttpServletRequest request, HttpServletResponse response){
        return userService.doLogin(loginVo,request,response);
    }
}
