package com.myself.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


import com.myself.mall.product.entity.ProductAttrValueEntity;
import com.myself.mall.product.service.ProductAttrValueService;
import com.myself.mall.product.vo.AttrRespVo;
import com.myself.mall.product.vo.AttrVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myself.mall.product.entity.AttrEntity;
import com.myself.mall.product.service.AttrService;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.R;



/**
 * 商品属性
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-15 20:15:58
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    /**
     * 查询属性规格
     */
    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrListForSpu(@RequestParam Map<String,Object> param, @PathVariable("attrType")String attrType, @PathVariable("catelogId") Long catelogId){
        PageUtils page = attrService.queryBaseAttrPage(param, catelogId,attrType);
        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){
//		AttrEntity attr = attrService.getById(attrId);
        AttrRespVo resp = attrService.getAttrInfo(attrId);
        return R.ok().put("attr", resp);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrEntity attr){
		attrService.save(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
//		attrService.updateById(attr);
        attrService.updateAttr(attr);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
