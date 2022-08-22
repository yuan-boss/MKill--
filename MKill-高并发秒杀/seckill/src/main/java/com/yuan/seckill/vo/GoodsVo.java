package com.yuan.seckill.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuan.seckill.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @module:
 * @description: 商品返回对象
 * @author: yuan_boss
 * @create: 2022-08-20 16:11
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsVo extends Goods {

    private BigDecimal seckillPrice;
    private Integer stockCount;
    @JsonFormat(locale = "zh", timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    private Date endDate;


}
