package com.myself.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.ware.entity.WareInfoEntity;
import com.myself.mall.ware.vo.FareVo;

import java.util.Map;

/**
 * 仓库信息
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:14:33
 */
public interface WareInfoService extends IService<WareInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    FareVo getFare(Long addrId);
}

