package com.myself.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.myself.mall.product.entity.AttrEntity;
import com.myself.mall.product.service.AttrService;
import com.myself.mall.product.vo.AttrGroupWithAttrsVo;
import com.myself.mall.product.vo.SpuItemAttrGroup;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private AttrService attrService;

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

    /**
     * ????????????id ???????????????????????????????????????????????????
     */
    @Override
    public List<AttrGroupWithAttrsVo> getAttrGroupWithAttrByCatelogId(Long catelogId) {

        // 1.??????????????????id???????????????
        List<AttrGroupEntity> attrGroupEntities = this.list(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));

        // 2.??????????????????
        List<AttrGroupWithAttrsVo> collect = attrGroupEntities.stream().map(group ->{
            // ?????????????????????
            AttrGroupWithAttrsVo attrVo = new AttrGroupWithAttrsVo();
            BeanUtils.copyProperties(group, attrVo);
            // ????????????id????????????????????????????????????vo
            List<AttrEntity> attrs = attrService.getRelationAttr(attrVo.getAttrGroupId());
            attrVo.setAttrs(attrs);
            return attrVo;
        }).collect(Collectors.toList());
        return collect;
    }
    @Override
    public List<SpuItemAttrGroup> getAttrGroupWithAttrsBySpuId(Long spuId, Long catalogId) {

        // 1.?????????Spu???????????????????????????????????? ?????????????????????????????????????????????
        // 1.1 ??????????????????
        AttrGroupDao baseMapper = this.getBaseMapper();

        return baseMapper.getAttrGroupWithAttrsBySpuId(spuId, catalogId);
    }
}