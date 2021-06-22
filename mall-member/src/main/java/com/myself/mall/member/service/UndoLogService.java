package com.myself.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.member.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:04:31
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

