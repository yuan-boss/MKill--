package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.utils.vo.LoginVo;
import com.yuan.seckill.utils.vo.RespBean;

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
    RespBean doLogin(LoginVo loginVo);
}
