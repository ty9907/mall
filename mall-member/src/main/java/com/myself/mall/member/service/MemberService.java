package com.myself.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myself.common.utils.PageUtils;
import com.myself.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author ty
 * @email sunlightcs@gmail.com
 * @date 2021-06-21 22:04:30
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

