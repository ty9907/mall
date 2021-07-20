package com.myself.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> getListTree();
}

