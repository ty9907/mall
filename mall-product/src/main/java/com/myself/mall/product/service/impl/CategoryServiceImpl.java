package com.myself.mall.product.service.impl;

import com.myself.mall.product.vo.Catalog3Vo;
import com.myself.mall.product.vo.Catelog2Vo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.Query;

import com.myself.mall.product.dao.CategoryDao;
import com.myself.mall.product.entity.CategoryEntity;
import com.myself.mall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> getListTree() {
        //查询所有分类
        List<CategoryEntity> list = baseMapper.selectList(null);
        List<CategoryEntity> treeList = list.stream().filter(item -> item.getParentCid() == 0l).map(item -> {
            item.setChildren(getChildren(item, list));
            return item;
        }).collect(Collectors.toList());
        return treeList;
    }

    private List<CategoryEntity> getChildren(CategoryEntity item, List<CategoryEntity> list) {
        List<CategoryEntity> children=new ArrayList<CategoryEntity>();
        children = list.stream().filter(category -> category.getParentCid() == item.getCatId()).map(category -> {
            category.setChildren(getChildren(category, list));
            return category;
        }).collect(Collectors.toList());
        return children;
    }

    @Override
    public Long[] selectCatePath(Long catId) {
        List<Long> cateePathList=new ArrayList<>();
        cateePathList.add(catId);
        CategoryEntity cate = this.getById(catId);
        while(cate.getParentCid()!=0){
            CategoryEntity newCate = this.getById(cate.getParentCid());
            cateePathList.add(newCate.getCatId());
            cate=newCate;
        }
        Collections.reverse(cateePathList);
        Long[] catePath = cateePathList.toArray(new Long[0]);
        return catePath;
    }

    /**
     * @Cacheable: 当前方法的结果需要缓存 并指定缓存名字
     *  缓存的value值 默认使用jdk序列化
     *  默认ttl时间 -1
     *	key: 里面默认会解析表达式 字符串用 ''
     *
     *  自定义:
     *  	1.指定生成缓存使用的key
     *  	2.指定缓存数据存活时间	[配置文件中修改]
     *  	3.将数据保存为json格式
     *
     *  sync = true: --- 开启同步锁
     *
     */
    @Cacheable(value = {"category"}, key = "#root.method.name", sync = true)
    @Override
    public List<CategoryEntity> getLevel1Categorys() {
        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("cat_level", 1));
        // 测试能否缓存null值
//		return null;
    }

    @Cacheable(value = "category", key = "#root.methodName")
    @Override
    public Map<String, List<Catelog2Vo>> getCatelogJson() {
        List<CategoryEntity> entityList = baseMapper.selectList(null);
        // 查询所有一级分类
        List<CategoryEntity> level1 = getCategoryEntities(entityList, 0L);
        Map<String, List<Catelog2Vo>> parent_cid = level1.stream().collect(Collectors.toMap(k -> k.getCatId().toString(), v -> {
            // 拿到每一个一级分类 然后查询他们的二级分类
            List<CategoryEntity> entities = getCategoryEntities(entityList, v.getCatId());
            List<Catelog2Vo> catelog2Vos = null;
            if (entities != null && entities.size()>0) {
                catelog2Vos = entities.stream().map(l2 -> {
                    Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), l2.getName(), l2.getCatId().toString(), null);
                    // 找当前二级分类的三级分类
                    List<CategoryEntity> level3 = getCategoryEntities(entityList, l2.getCatId());
                    // 三级分类有数据的情况下
                    if (level3 != null) {
                        List<Catalog3Vo> catalog3Vos = level3.stream().map(l3 -> new Catalog3Vo(l3.getCatId().toString(), l3.getName(), l2.getCatId().toString())).collect(Collectors.toList());
                        catelog2Vo.setCatalog3List(catalog3Vos);
                    }
                    return catelog2Vo;
                }).collect(Collectors.toList());
            }
            return catelog2Vos;
        }));
        return parent_cid;
    }

    private List<CategoryEntity> getCategoryEntities(List<CategoryEntity> entityList, Long parent_cid) {
        return entityList.stream().filter(item -> item.getParentCid() == parent_cid).collect(Collectors.toList());
    }

}