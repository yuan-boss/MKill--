package com.yuan.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuan.seckill.entity.Goods;
import com.yuan.seckill.mapper.GoodsMapper;
import com.yuan.seckill.service.IGoodsService;
import com.yuan.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuanboss
 * @since 2022-08-20
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {


    @Autowired
    private GoodsMapper goodsMapper;

    /**
     * @Author yuan_boss
     * @Description 获取商品列表
     * @Date 16:18 2022/8/20
     * @Param
     * @return
     **/
    @Override
    public List<GoodsVo> findGoodVo() {
        return goodsMapper.findGoodsVo();
    }

    /**
     * @Author yuan_boss
     * @Description 获取商品详情
     * @Date 17:22 2022/8/20
     * @Param
     * @return
     **/
    @Override
    public GoodsVo findGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.findGoodsVoByGoodsId(goodsId);
    }
}
