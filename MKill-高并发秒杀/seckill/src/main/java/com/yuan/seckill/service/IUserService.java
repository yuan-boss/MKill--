package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.vo.LoginVo;
import com.yuan.seckill.vo.RespBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-18
 */
public interface IUserService extends IService<User> {

    /**
     * @Author yuan_boss
     * @Description 登录逻辑
     * @Date 15:00 2022/8/19
     * @Param
     * @return
     **/
    RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response);

    /**
     * @Author yuan_boss
     * @Description 根据cookie获取用户
     * @Date 10:31 2022/8/20
     * @Param
     * @return
     **/
    User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response);

    /**
     * @Author yuan_boss
     * @Description 更新密码
     * @Date 9:23 2022/8/23
     * @Param
     * @return
     **/
    RespBean updatePassword(String userTicket,String password,HttpServletRequest request,HttpServletResponse response);
}
