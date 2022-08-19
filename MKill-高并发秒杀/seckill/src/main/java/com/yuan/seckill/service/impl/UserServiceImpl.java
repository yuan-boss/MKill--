package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.mapper.UserMapper;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.utils.MD5Util;
import com.yuan.seckill.utils.ValidatorUtil;
import com.yuan.seckill.utils.vo.LoginVo;
import com.yuan.seckill.utils.vo.RespBean;
import com.yuan.seckill.utils.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;
    /**
     * @Author yuan_boss
     * @Description 登录逻辑
     * @Date 15:00 2022/8/19
     * @Param
     * @return
     **/
    @Override
    public RespBean doLogin(LoginVo loginVo) {
        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        //参数校验
        //判断手机号或密码是否为空
//        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
//            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
//        }
        //校验手机号格式
//        if (!ValidatorUtil.isMobile(mobile)){
//            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
//        }
        //根据手机号获取用户
        User user = userMapper.selectById(mobile);
        //判断是否存在这个用户
        if (null == user){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        //校验密码
        if (!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }

        return RespBean.success();
    }
}
