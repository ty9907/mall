package com.myself.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.to.SkuReductionTO;
import com.myself.common.utils.PageUtils;
import com.myself.mall.coupon.entity.SkuFullReductionEntity;

import java.util.Map;

/**
 * 商品满减信息
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 21:53:37
 */
public interface SkuFullReductionService extends IService<SkuFullReductionEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuReduction(SkuReductionTO reductionTo);
}

