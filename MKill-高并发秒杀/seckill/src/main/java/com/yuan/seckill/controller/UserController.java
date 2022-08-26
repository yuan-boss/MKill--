package com.yuan.seckill.controller;


import com.yuan.seckill.entity.User;
import com.yuan.seckill.rabbitmq.MQSender;
import com.yuan.seckill.vo.RespBean;
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
 * @since 2022-08-18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender sender;

    /**
     * @Author yuan_boss
     * @Description 测试用户信息
     * @Date 13:49 2022/8/22
     * @Param
     * @return
     **/
    @RequestMapping("/info")
    @ResponseBody
    public RespBean info(User user){
        return RespBean.success(user);
    }


//    /**
//     * @Author yuan_boss
//     * @Description 测试发送RabbitMQ的消息
//     * @Date 11:32 2022/8/24
//     * @Param
//     * @return
//     **/
//    @RequestMapping("/mq")
//    @ResponseBody
//    public void mq(){
//        sender.send("hello");
//    }
//
//    /**
//     * @Author yuan_boss
//     * @Description fanout模式
//     * @Date 14:20 2022/8/24
//     * @Param
//     * @return
//     **/
//    @RequestMapping("/mq/fanout")
//    public void mq01(){
//        sender.send("hello");
//    }
//    /**
//     * @Author yuan_boss
//     * @Description direct模式
//     * @Date 14:41 2022/8/24
//     * @Param
//     * @return
//     **/
//    @RequestMapping("/mq/direct01")
//    public void mq02(){
//        sender.send01("Hello,red");
//    }
//    @RequestMapping("/mq/direct02")
//    public void mq03(){
//        sender.send02("Hello，green");
//    }
//    /**
//     * @Author yuan_boss
//     * @Description Topic模式
//     * @Date 15:02 2022/8/24
//     * @Param
//     * @return
//     **/
//    @RequestMapping("/mq/topic01")
//    public void mq04(){
//        sender.send03("Hello，Red");
//    }
//    @RequestMapping("/mq/topic02")
//    public void mq05(){
//        sender.send04("Hello，Green");
//    }
//
//    /**
//     * @Author yuan_boss
//     * @Description Headers模式
//     * @Date 15:18 2022/8/24
//     * @Param
//     * @return
//     **/
//    @RequestMapping("/mq/header01")
//    public void mq06(){
//        sender.send05("Hello，Header01");
//    }
//    @RequestMapping("/mq/header02")
//    public void mq07(){
//        sender.send06("Hello，Header02");
//    }
}
