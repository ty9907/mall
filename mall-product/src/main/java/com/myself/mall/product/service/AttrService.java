package com.myself.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.product.entity.AttrEntity;
import com.myself.mall.product.vo.AttrGroupRelationVo;
import com.myself.mall.product.vo.AttrRespVo;
import com.myself.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attrVo);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrGroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);

    /**
     * 在指定的集合里面挑出可检索的属性
     */
    List<Long> selectSearchAttrIds(List<Long> attrIds);
}

