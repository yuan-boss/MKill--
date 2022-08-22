package com.yuan.seckill.vo;

import com.yuan.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @module:
 * @description:登录参数
 * @author: yuan_boss
 * @create: 2022-08-19 14:47
 **/
@Data
public class LoginVo {
    @NotNull
    @IsMobile
    private String mobile;
    @NotNull
    @Length(min = 32)//MD5加密后长度至少32位
    private String password;
}
