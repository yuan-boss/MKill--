package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.exception.GlobalException;
import com.yuan.seckill.mapper.UserMapper;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.utils.CookieUtil;
import com.yuan.seckill.utils.MD5Util;
import com.yuan.seckill.utils.UUIDUtil;
import com.yuan.seckill.vo.LoginVo;
import com.yuan.seckill.vo.RespBean;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * @Author yuan_boss
     * @Description 登录逻辑
     * @Date 15:00 2022/8/19
     * @Param
     * @return
     **/
    @Override
    public RespBean doLogin(LoginVo loginVo, HttpServletRequest request, HttpServletResponse response) {
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
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }
        //校验密码
        if (!MD5Util.fromPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            throw new GlobalException(RespBeanEnum.LOGIN_ERROR);
        }

        //生成cookie
        String ticket = UUIDUtil.uuid();
        //request.getSession().setAttribute("ticket",user);
        //将用户信息存入redis中
        redisTemplate.opsForValue().set("user:" + ticket,user);
        CookieUtil.setCookie(request,response,"userTicket",ticket);
        return RespBean.success(ticket);
    }

    /**
     * @Author yuan_boss
     * @Description 根据cookie获取用户
     * @Date 10:33 2022/8/20
     * @Param
     * @return
     **/
    @Override
    public User getUserByCookie(String userTicket,HttpServletRequest request,HttpServletResponse response) {

        if (StringUtils.isEmpty(userTicket)) {
            return null;
        }
        User user = (User) redisTemplate.opsForValue().get("user:" + userTicket);
        if (user != null) {
            CookieUtil.setCookie(request,response,"userTicket",userTicket);
        }
        return user;
    }

    /**
     * @Author yuan_boss
     * @Description 更新密码
     * @Date 9:26 2022/8/23
     * @Param
     * @return
     **/
    @Override
    public RespBean updatePassword(String userTicket,  String password,HttpServletRequest request,HttpServletResponse response) {
        User user = getUserByCookie(userTicket, request, response);
        if (user == null) {
            throw new GlobalException(RespBeanEnum.MOBILE_NOT_EXIST);
        }
        user.setPassword(MD5Util.inputPassToDBPass(password,user.getSalt()));
        int result = userMapper.updateById(user);
        if (1 == result) {
            //删除redis
            redisTemplate.delete("user:"+userTicket);
            return RespBean.success();
        }

        return RespBean.error(RespBeanEnum.PASSWORD_UPDATE_FAIL);
    }
}
