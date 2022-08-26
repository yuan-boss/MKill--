package com.yuan.seckill.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.utils.CookieUtil;
import com.yuan.seckill.vo.RespBean;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @module:
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-26 10:07
 **/
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            User user = getUser(request,response);
            UserContext.setUser(user);
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (accessLimit == null) {
                return true;
            }
            int second = accessLimit.second();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();
            String key = request.getRequestURI();
            if (needLogin){
                if (user == null){
                    render(response, RespBeanEnum.SESSION_ERROR);
                    return false;
                }
                key += ":" +user.getId();
            }
            ValueOperations valueOperations = redisTemplate.opsForValue();
            Integer count = (Integer) valueOperations.get(key);
            if (count == null) {
                valueOperations.set(key,1,second, TimeUnit.SECONDS);
            }else if (count < maxCount){
                valueOperations.increment(key);
            }else {
                render(response,RespBeanEnum.ACCESS_LIMIT_REACHED);
                return false;
            }

        }
        return true;
    }

    /**
     * @Author yuan_boss
     * @Description 构建返回对象
     * @Date 10:30 2022/8/26
     * @Param
     * @return
     **/
    private void render(HttpServletResponse response, RespBeanEnum respBeanEnum) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        RespBean respBean = RespBean.error(respBeanEnum);
        out.write(new ObjectMapper().writeValueAsString(respBean));
        out.flush();
        out.close();

    }

    /**
     * @Author yuan_boss
     * @Description 获取当前登录用户
     * @Date 10:15 2022/8/26
     * @Param
     * @return
     **/
    private User getUser(HttpServletRequest request, HttpServletResponse response) {
        String ticket = CookieUtil.getCookieValue(request, "userTicket");
        if (StringUtils.isEmpty(ticket)) {
            return null;
        }

        return userService.getUserByCookie(ticket,request,response);
    }
}
