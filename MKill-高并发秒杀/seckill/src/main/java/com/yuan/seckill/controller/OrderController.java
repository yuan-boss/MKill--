package com.yuan.seckill.controller;


import com.yuan.seckill.entity.User;
import com.yuan.seckill.service.IOrderService;
import com.yuan.seckill.vo.OrderDetailVo;
import com.yuan.seckill.vo.RespBean;
import com.yuan.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;
    /**
     * @Author yuan_boss
     * @Description 订单详情
     * @Date 15:44 2022/8/23
     * @Param
     * @return
     **/
    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user,Long orderId){
        if (user == null){
           return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVo detail = orderService.detail(orderId);
        return RespBean.success(detail);

    }

}
