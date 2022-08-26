package com.yuan.seckill.config;

import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IUserService;
import com.yuan.seckill.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @module:
 * @description: 自定义用户参数
 * @author: yuan_boss
 * @create: 2022-08-20 11:03
 **/
@Component
public class UserArgumentResolvers implements HandlerMethodArgumentResolver {

    @Autowired
    private IUserService userService;

    //如果参数类型是User，就会走第二个方法
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> clazz = parameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
//        HttpServletResponse response = webRequest.getNativeResponse(HttpServletResponse.class);
//        String ticket = CookieUtil.getCookieValue(request, "userTicket");
//        if (StringUtils.isEmpty(ticket)) {
//            return null;
//        }
//
//        return userService.getUserByCookie(ticket,request,response);

        return UserContext.getUser();
    }
}
