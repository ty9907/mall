package com.myself.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.to.OrderTo;
import com.myself.common.to.StockLockedTo;
import com.myself.common.to.es.SkuHasStockVo;
import com.myself.common.utils.PageUtils;
import com.myself.mall.ware.entity.WareSkuEntity;
import com.myself.mall.ware.vo.WareSkuLockVo;

import java.util.List;
import java.util.Map;

/**
 * εεεΊε­
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:14:34
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<SkuHasStockVo> getSkuHasStock(List<Long> skuIds);

    double addStock(Long skuId, Long wareId, Integer skuNum);

    Boolean orderLockStock(WareSkuLockVo vo);

    void unlockStock(OrderTo to);

    void unlockStock(StockLockedTo to);
}

