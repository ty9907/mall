package com.myself.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.product.entity.SpuImagesEntity;

import java.util.List;
import java.util.Map;

/**
 * spu图片
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveImages(Long id, List<String> images);
}

