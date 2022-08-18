package com.yuan.seckill.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @module:
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-11 11:05
 **/

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/hello")
    public String hello(Model model){

        model.addAttribute("name","yuanboss");
        return "hello";
    }
}
