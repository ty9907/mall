package com.myself.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.Query;

import com.myself.mall.product.dao.ProductAttrValueDao;
import com.myself.mall.product.entity.ProductAttrValueEntity;
import com.myself.mall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<ProductAttrValueEntity> baseAttrListForSpu(Long spuId) {
        return this.baseMapper.selectList(new LambdaQueryWrapper<ProductAttrValueEntity>().eq(ProductAttrValueEntity::getSpuId, spuId));
    }

    @Override
    public void saveProductAttr(List<ProductAttrValueEntity> collect) {
        this.saveBatch(collect);
    }
}