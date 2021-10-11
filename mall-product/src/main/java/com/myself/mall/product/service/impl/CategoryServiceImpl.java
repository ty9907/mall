package com.myself.mall.product.service.impl;

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
}