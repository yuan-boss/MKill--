package com.yuan.seckill.controller;


import com.yuan.seckill.entity.User;
import com.yuan.seckill.vo.RespBean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    /**
     * @Author yuan_boss
     * @Description 测试用户信息
     * @Date 13:49 2022/8/22
     * @Param
     * @return
     **/
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }
}
