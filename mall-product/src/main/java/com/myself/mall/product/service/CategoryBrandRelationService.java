package com.myself.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.product.entity.BrandEntity;
import com.myself.mall.product.entity.CategoryBrandRelationEntity;

import java.util.List;
import java.util.Map;

/**
 * 品牌分类关联
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
public interface CategoryBrandRelationService extends IService<CategoryBrandRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
    void saveDetail(CategoryBrandRelationEntity categoryBrandRelation);
    void updateCategory(Long catId, String name);
    void updateBrand(Long brandId, String name);
    List<BrandEntity> getBrandsByCatId(Long catId);
}

