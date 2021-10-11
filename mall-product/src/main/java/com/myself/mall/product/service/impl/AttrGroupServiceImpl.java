package com.myself.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.Query;

import com.myself.mall.product.dao.AttrGroupDao;
import com.myself.mall.product.entity.AttrGroupEntity;
import com.myself.mall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catId) {
        if(0L ==catId){
            return queryPage(params);
        }else{
            LambdaQueryWrapper<AttrGroupEntity> wrapper=new LambdaQueryWrapper<>();
            wrapper.eq(AttrGroupEntity::getCatelogId,catId);
            if(params.get("key")!=null){
                wrapper.and(obj->{
                    obj.like(params.get("key")!=null,AttrGroupEntity::getAttrGroupName,params.get("key")).or()
                            .like(params.get("key")!=null,AttrGroupEntity::getDescript,params.get("key"));
                });
            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }
    }
}