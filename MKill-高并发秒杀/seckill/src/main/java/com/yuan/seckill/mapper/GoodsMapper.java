package com.yuan.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuan.seckill.entity.Goods;
import com.yuan.seckill.vo.GoodsVo;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    /**
     * @Author yuan_boss
     * @Description 获取商品列表
     * @Date 16:18 2022/8/20
     * @Param
     * @return
     **/

    List<GoodsVo> findGoodsVo();

    /**
     * @Author yuan_boss
     * @Description 获取商品详情
     * @Date 17:22 2022/8/20
     * @Param
     * @return
     **/
    GoodsVo findGoodsVoByGoodsId(Long goodsId);
}
