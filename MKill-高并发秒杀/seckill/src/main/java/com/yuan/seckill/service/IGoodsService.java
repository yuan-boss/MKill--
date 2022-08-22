package com.yuan.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yuan.seckill.entity.Goods;
import com.yuan.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
public interface IGoodsService extends IService<Goods> {

    /**
     * @Author yuan_boss
     * @Description 获取商品列表
     * @Date 17:20 2022/8/20
     * @Param
     * @return
     **/
    List<GoodsVo> findGoodVo();

    /**
     * @Author yuan_boss
     * @Description 获取商品详情
     * @Date 17:20 2022/8/20
     * @Param
     * @return
     **/
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
