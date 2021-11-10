package com.myself.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.product.entity.SpuInfoEntity;
import com.myself.mall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo vo);

    void saveBatchSpuInfo(SpuInfoEntity spuInfoEntity);

    void up(Long spuId);

    /**
     * SPU模糊查询
     */
    PageUtils queryPageByCondition(Map<String, Object> params);
}

