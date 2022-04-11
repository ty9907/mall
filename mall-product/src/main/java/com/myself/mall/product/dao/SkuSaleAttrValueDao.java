package com.myself.mall.product.dao;

import com.myself.mall.product.entity.SkuSaleAttrValueEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.myself.mall.product.vo.ItemSaleAttrVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * sku销售属性&值
 * 
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
@Mapper
public interface SkuSaleAttrValueDao extends BaseMapper<SkuSaleAttrValueEntity> {
    List<ItemSaleAttrVo> getSaleAttrsBuSpuId(@Param("spuId")Long spuId);

    List<String> getSkuSaleAttrValuesAsStringList(@Param("skuId") Long skuId);
}
