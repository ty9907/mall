package com.myself.mall.product.dao;

import com.myself.mall.product.entity.SpuInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * spu信息
 * 
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
@Mapper
public interface SpuInfoDao extends BaseMapper<SpuInfoEntity> {

    /**
     * 修改上架成功的商品的状态
     */
    void updateSpuStatus(@Param("spuId") Long spuId, @Param("code") int code);
}
