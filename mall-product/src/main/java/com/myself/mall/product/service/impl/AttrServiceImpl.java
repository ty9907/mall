package com.myself.mall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.myself.common.constants.ProductConstant;
import com.myself.mall.product.dao.AttrAttrgroupRelationDao;
import com.myself.mall.product.dao.AttrGroupDao;
import com.myself.mall.product.dao.CategoryDao;
import com.myself.mall.product.entity.AttrAttrgroupRelationEntity;
import com.myself.mall.product.entity.AttrGroupEntity;
import com.myself.mall.product.entity.CategoryEntity;
import com.myself.mall.product.service.AttrAttrgroupRelationService;
import com.myself.mall.product.service.CategoryService;
import com.myself.mall.product.vo.AttrGroupRelationVo;
import com.myself.mall.product.vo.AttrRespVo;
import com.myself.mall.product.vo.AttrVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.Query;

import com.myself.mall.product.dao.AttrDao;
import com.myself.mall.product.entity.AttrEntity;
import com.myself.mall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {

    @Resource
    private AttrAttrgroupRelationDao relationDao;

    @Autowired
    private AttrAttrgroupRelationService relationService;

    @Resource
    private AttrGroupDao attrGroupDao;

    @Resource
    private CategoryDao categoryDao;

    @Resource
    private CategoryService categoryService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Transactional
    @Override
    public void saveAttr(AttrVo attrVo) {
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attrVo, attrEntity);
        //1?????????????????????
        this.save(attrEntity);
        //2?????????????????????
        if (attrVo.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attrVo.getAttrGroupId() != null) {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attrVo.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationEntity.setAttrSort(0);
            relationDao.insert(relationEntity);
        }
    }

    /**
     * ?????????????????????????????????
     */
    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String attrType) {
        QueryWrapper<AttrEntity> waWrapper = new QueryWrapper<AttrEntity>().eq("attr_type", "base".equalsIgnoreCase(attrType)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());
//        QueryWrapper<AttrEntity> waWrapper = new QueryWrapper<AttrEntity>();
        if (catelogId != ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode()) {
            // ????????? base ?????????????????? ??????1 ????????????0
            waWrapper.eq("catelog_id", catelogId);
        }
        String key = (String) params.get("key");
        if (!StringUtils.isEmpty(key)) {
            waWrapper.and((w) -> {
                w.eq("attr_id", key).or().like("attr_name", key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                waWrapper
        );
        PageUtils pageUtils = new PageUtils(page);
        // ?????????????????????????????????????????? ?????????
        List<AttrEntity> records = page.getRecords();
        // attrRespVos ????????????????????????Vo
        List<AttrRespVo> attrRespVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);
            // 1.??????????????????????????????  ????????????????????????  ???attrRespVo ??????????????????
//            if("base".equalsIgnoreCase(attrType)){
                // attr??????????????? ??????????????????????????????????????????
                AttrAttrgroupRelationEntity entity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (entity != null && entity.getAttrGroupId() != null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(entity);
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }
//            }
            // 2.????????????id ???attrRespVo ????????????????????????
            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());
        pageUtils.setList(attrRespVos);
        return pageUtils;
    }

    @Override
    public AttrRespVo getAttrInfo(Long attrId) {
        AttrRespVo resp=new AttrRespVo();
        AttrEntity attrEntity = this.getById(attrId);
        BeanUtils.copyProperties(attrEntity,resp);

        AttrAttrgroupRelationEntity relation =
                relationDao.selectOne(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId, attrId));
        if(relation!=null){
            AttrGroupEntity groupEntity = attrGroupDao.selectById(relation.getAttrGroupId());
            if(groupEntity!=null){
                resp.setGroupName(groupEntity.getAttrGroupName());
                resp.setAttrGroupId(groupEntity.getAttrGroupId());
            }
        }
        CategoryEntity categoryEntity = categoryService.getById(resp.getCatelogId());
        if(categoryEntity!=null){
            resp.setCatelogName(categoryEntity.getName());
        }
        Long[] catePath = categoryService.selectCatePath(resp.getCatelogId());
        resp.setCatelogPath(catePath);

        return resp;
    }

    @Transactional
    @Override
    public void updateAttr(AttrVo attr) {
        AttrEntity attrEntity=new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        updateById(attrEntity);

        AttrAttrgroupRelationEntity relation=new AttrAttrgroupRelationEntity();
        relation.setAttrId(attrEntity.getAttrId());
        relation.setAttrGroupId(attr.getAttrGroupId());
        relationService.saveOrUpdate(relation, new LambdaUpdateWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrId,attr.getAttrId()));
    }

    @Override
    public List<AttrEntity> getRelationAttr(Long attrGroupId) {
        List<AttrEntity> result=new ArrayList<>();
        List<AttrAttrgroupRelationEntity> relations = relationService.list(new LambdaQueryWrapper<AttrAttrgroupRelationEntity>().eq(AttrAttrgroupRelationEntity::getAttrGroupId, attrGroupId));
        List<Long> attrIds = relations.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        if(!CollectionUtils.isEmpty(attrIds)){
            result=this.listByIds(attrIds);
        }
        return result;
    }

    /**
     * ??????????????????????????????
     */
    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        // ?????????????????????????????? AttrAttrgroupRelationEntity
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((v) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(v, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);
    }

    /**
     * ???????????????????????????????????????
     */
    @Override
    public PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId) {
        //1?????????????????????????????????????????????????????????????????????
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        // 2??????????????????????????????????????????????????????																									?????????????????????id???????????????????????????id
        //2.1)?????????????????????????????????
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        // ???????????????????????????????????????id
        List<Long> collect = group.stream().map(AttrGroupEntity::getAttrGroupId).collect(Collectors.toList());

        //2.2)????????????????????????????????????
        List<AttrAttrgroupRelationEntity> groupId = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", collect));
        // ??????????????????????????????????????????id?????????
        List<Long> attrIds = groupId.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());

        //2.3)?????????????????????????????????????????????????????????[???????????????????????????????????? ?????????????????????]
        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(attrIds != null && attrIds.size() > 0){
            wrapper.notIn("attr_id", attrIds);
        }
        // ??????????????????key???????????????????????? ??????????????????
        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        // ????????????????????????????????????
        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);

        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }

    /**
     * SELECT attr_id FROM `pms_attr` WHERE attr_id IN (?) AND search_type = 1
     */
    @Override
    public List<Long> selectSearchAttrIds(List<Long> attrIds) {
        return baseMapper.selectSearchAttrIds(attrIds);
    }
}