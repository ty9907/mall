package com.myself.mall.ware.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

////import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.myself.common.exception.BizCodeEnum;
import com.myself.common.exception.NotStockException;
import com.myself.common.to.es.SkuHasStockVo;
import com.myself.mall.ware.vo.WareSkuLockVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myself.mall.ware.entity.WareSkuEntity;
import com.myself.mall.ware.service.WareSkuService;
import com.myself.common.utils.PageUtils;
import com.myself.common.utils.R;



/**
 * 商品库存
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:14:34
 */
@Slf4j
@RestController
@RequestMapping("ware/waresku")
public class WareSkuController {
    @Autowired
    private WareSkuService wareSkuService;

    @PostMapping("/lock/order")
    public R orderLockStock(@RequestBody WareSkuLockVo vo){
        try {
            wareSkuService.orderLockStock(vo);
            return R.ok();
        } catch (NotStockException e) {
            log.warn("\n" + e.getMessage());
        }
        return R.error(BizCodeEnum.NOT_STOCK_EXCEPTION.getCode(), BizCodeEnum.NOT_STOCK_EXCEPTION.getMsg());
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:waresku:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = wareSkuService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:waresku:info")
    public R info(@PathVariable("id") Long id){
		WareSkuEntity wareSku = wareSkuService.getById(id);

        return R.ok().put("wareSku", wareSku);
    }

    /**
     * 查询sku是否有库存
     * 返回当前id stock量
     */
    @PostMapping("/hasStock")
//	public List<SkuHasStockVo> getSkuHasStock(@RequestBody List<Long> SkuIds){
    public R getSkuHasStock(@RequestBody List<Long> SkuIds){
        List<SkuHasStockVo> vos = wareSkuService.getSkuHasStock(SkuIds);
        return R.ok().setData(vos);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:waresku:save")
    public R save(@RequestBody WareSkuEntity wareSku){
		wareSkuService.save(wareSku);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:waresku:update")
    public R update(@RequestBody WareSkuEntity wareSku){
		wareSkuService.updateById(wareSku);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("ware:waresku:delete")
    public R delete(@RequestBody Long[] ids){
		wareSkuService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
