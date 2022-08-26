package com.yuan.seckill.config;

import com.yuan.seckill.entity.User;

/**
 * @module:
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-26 10:17
 **/
public class UserContext {

    private static ThreadLocal<User>  userHolder = new ThreadLocal<>();

    public static void setUser(User user){
        userHolder.set(user);
    }

    public static User getUser(){
        return userHolder.get();
    }
}
