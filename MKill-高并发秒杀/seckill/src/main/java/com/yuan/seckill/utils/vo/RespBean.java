package com.yuan.seckill.utils.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @module: 公共返回对象
 * @description:
 * @author: yuan_boss
 * @create: 2022-08-19 14:27
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    /**
     * @Author yuan_boss
     * @Description 成功的返回结果
     * @Date 14:31 2022/8/19
     * @Param
     * @return
     **/
    public static RespBean success(){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), null);
    }

    /**
     * @Author yuan_boss
     * @Description 成功的返回结果（带对象）
     * @Date 14:32 2022/8/19
     * @Param obj 传入的对象
     * @return
     **/
    public static RespBean success(Object obj){
        return new RespBean(RespBeanEnum.SUCCESS.getCode(), RespBeanEnum.SUCCESS.getMessage(), obj);
    }
    /**
     * @Author yuan_boss
     * @Description 失败的返回结果
     * @Date 14:34 2022/8/19
     * @Param respBeanEnum 枚举
     * @return
     **/

    public static RespBean error(RespBeanEnum respBeanEnum){
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(),null);
    }

    /**
     * @Author yuan_boss
     * @Description 失败的返回结果（带对象）
     * @Date 14:35 2022/8/19
     * @Param  respBeanEnum 枚举
     * @Param  obj 对象
     * @return
     **/
    public static RespBean error(RespBeanEnum respBeanEnum ,Object obj) {
        return new RespBean(respBeanEnum.getCode(), respBeanEnum.getMessage(),obj);
    }


}
