package com.myself.mall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.coupon.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 21:53:37
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

